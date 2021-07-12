/**
 * FileName: TypeServiceImpl
 * Author:   嘉平十七
 * Date:     2021/6/18 13:54
 * Description:
 */
package com.chen.blog.service.impl;

import com.chen.blog.dao.TypeRepository;
import com.chen.blog.domain.Type;
import com.chen.blog.service.TypeService;
import com.chen.blog.utils.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;

    //保存分类
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    //通过ID获取分类
    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.getById(id);
    }

    //通过类别名获取分类
    @Override
    public Type getTypeByName(String typeName) {
        return typeRepository.findByName(typeName);
    }

    //列出所有分类
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    //列出所有分类并分页
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    //列出热门分类
    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return typeRepository.findTop(pageable);
    }

    //更新分类
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.getById(id);
        if (t == null){
            throw new NotFoundException("不存在该分类");
        }
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    //删除分类
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}