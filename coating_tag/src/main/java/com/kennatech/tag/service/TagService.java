package com.kennatech.tag.service;

import com.kennatech.coating.utils.IdWorker;
import com.kennatech.tag.dao.TagDao;
import com.kennatech.tag.entity.QueryVo;
import com.kennatech.tag.pojo.Tag;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagService {

    @Autowired
    private TagDao tagDao;

    @Autowired
    private IdWorker idWorker;

    // 增加标签
    public void add(QueryVo queryVo) {

        if (StringUtils.isEmpty(queryVo.getName())) {
            throw new RuntimeException("必须提交标签名称");
        }
        // 先查询是否存在相同name，避免重复
        Tag tagFromDB = tagDao.findByName(queryVo.getName());
        if (tagFromDB != null) {
            throw new RuntimeException("已经存在相同名称的标签");
        }
        Tag tag = new Tag(String.valueOf(idWorker.nextId()), queryVo.getName(), "1");
        tagDao.save(tag);
    }

    // 根据id 逻辑删除
    public void deleteById(String id) {
        tagDao.updateStatus(id);
    }

    // 修改标签
    public void update(QueryVo queryVo) {

        Tag tag = tagDao.findById(queryVo.getId()).orElse(null);
        if (tag == null) {
            throw new RuntimeException("未查询到需要修改的标签");
        }

        if (StringUtils.isNotEmpty(queryVo.getName())) {
            tag.setName(queryVo.getName());
        }

        if (StringUtils.isNotEmpty(queryVo.getStatus())
                && "1".equals(queryVo.getStatus())
                || "0".equals(queryVo.getStatus())) {
            tag.setStatus(queryVo.getStatus());
        }

        tagDao.save(tag);


    }

    public Tag findById(String id) {
        return tagDao.findById(id).orElse(null);
//        return tagDao.findById(id).get();
    }

    public List<Tag> findAll() {

        return tagDao.findAllByStatusNot("0");
    }
}
