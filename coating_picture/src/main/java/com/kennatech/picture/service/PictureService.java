package com.kennatech.picture.service;

import com.kennatech.coating.entity.Result;
import com.kennatech.coating.entity.StatusCode;
import com.kennatech.coating.utils.IdWorker;
import com.kennatech.picture.client.FileSystemClient;
import com.kennatech.picture.dao.CategoryDao;
import com.kennatech.picture.dao.PictureDao;
import com.kennatech.picture.dao.TagDao;
import com.kennatech.picture.dao.TypeDao;
import com.kennatech.picture.entity.QueryVo;
import com.kennatech.picture.pojo.Category;
import com.kennatech.picture.pojo.Picture;
import com.kennatech.picture.pojo.Tag;
import com.kennatech.picture.pojo.Type;
import org.apache.commons.lang.StringUtils;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class PictureService {


    @Autowired
    private PictureDao pictureDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private FileSystemClient fileSystemClient;


    // 增加
    public Picture add(MultipartFile multipartFile, QueryVo queryVo) throws IOException, MyException {

        Result result = fileSystemClient.upload(multipartFile);

        if (result.getCode() == StatusCode.ERROR) {
            throw new RuntimeException(result.getMessage());
        }
        Picture picture = new Picture();

        // 设置图片url
        picture.setUrl((String) result.getData());


        // 1、设置id
        picture.setId(String.valueOf(idWorker.nextId()));

        // 2、设置名称name
        picture.setName(queryVo.getName());

        // 3、设置图片所属大类category
        Optional categoryOptional = categoryDao.findById(queryVo.getCategory());
        if (!categoryOptional.isPresent()) {
            throw new RuntimeException("图片上传失败，未查询到指定的大类");
        }
        picture.setCategory((Category) categoryOptional.get());

        // 4、设置小类type
        Optional typeOptional = typeDao.findById(queryVo.getType());
        if (!typeOptional.isPresent()) {
            throw new RuntimeException("图片上传失败，未查询到指定的小类");
        }
        picture.setType((Type) typeOptional.get());
//        picture.setType(typeDao.findById(queryVo.getType()).get());

        // 5、设置描述description
        picture.setDescription(queryVo.getDescription());

        // 6、设置标签tags
        String[] tags = queryVo.getTags();
        Set<Tag> tagsSet = new HashSet<Tag>();
        for (String tag : tags) {
            tagsSet.add(tagDao.findById(tag).get());
        }
        picture.setTags(tagsSet);

        // 7、设置上传时间
        picture.setUploadDate(new Timestamp(new Date().getTime()));


        picture.setStatus(1);

        System.out.println(picture);

        pictureDao.save(picture);

        return picture;

    }

    // 逻辑删除，将status值设为0
    public void deleteById(String id) {
        pictureDao.updateStatus(id);
    }

    // 修改
    public void update(String id, MultipartFile multipartFile, QueryVo queryVo) throws IOException, MyException {

        Picture picture = pictureDao.findById(id).orElse(null);

        if (picture == null) {
            throw new RuntimeException("未找到要修改的图片");
        }

        if (StringUtils.isNotEmpty(queryVo.getName())) {
            picture.setName(queryVo.getName());
        }

        if (StringUtils.isNotEmpty(queryVo.getDescription())) {
            picture.setDescription(queryVo.getDescription());
        }

        if (StringUtils.isNotEmpty(queryVo.getType())) {
            Type type = typeDao.findById(queryVo.getType()).orElse(null);
            if (type != null) {
                picture.setType(type);
            }
        }
        if (StringUtils.isNotEmpty(queryVo.getCategory())) {
            Category category = categoryDao.findById(queryVo.getCategory()).orElse(null);
            if (category != null) {
                picture.setCategory(category);
            }
        }

        if (queryVo.getTags() != null && queryVo.getTags().length >= 1) {
            String[] tags = queryVo.getTags();
            Set<Tag> tagsSet = new HashSet<Tag>();
            for (String tag : tags) {
                tagsSet.add(tagDao.findById(tag).get());
            }
            picture.setTags(tagsSet);
        }

        if (StringUtils.isNotEmpty(queryVo.getDescription())) {
            picture.setDescription(queryVo.getDescription());
        }

        if (multipartFile != null) {
            Result result = fileSystemClient.upload(multipartFile);
            if (result.getCode() == StatusCode.ERROR) {
                throw new RuntimeException(result.getMessage());
            }

            picture.setUrl((String) result.getData());
        }
        pictureDao.save(picture);

    }

    // 分页查询
    public Page<Picture> search(int page, int size, QueryVo queryVo) {
        Specification<Picture> specification = new Specification<Picture>() {
            @Override
            public Predicate toPredicate(Root<Picture> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();

                // 剔除status等于0的记录（等于0的是被逻辑删除的）
                predicateList.add(criteriaBuilder.notEqual(root.get("status").as(Integer.class), 0));

                if (StringUtils.isNotEmpty(queryVo.getDescription())) {
                    predicateList.add(criteriaBuilder.like(root.get("description").as(String.class), "%" + queryVo.getDescription() + "%"));
                }

                if (StringUtils.isNotEmpty(queryVo.getName())) {
                    predicateList.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + queryVo.getName() + "%"));
                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("uploadDate").descending());
        return pictureDao.findAll(specification, pageRequest);

    }

    // 根据id查询
    public Picture findById(String id) {
        return pictureDao.findById(id).get();
    }
}

