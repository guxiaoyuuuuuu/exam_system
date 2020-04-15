package org.jeecg.modules.exam.service.impl;

import org.jeecg.modules.exam.entity.Course;
import org.jeecg.modules.exam.mapper.CourseMapper;
import org.jeecg.modules.exam.service.ICourseService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 科目表
 * @Author: jeecg-boot
 * @Date:   2020-04-15
 * @Version: V1.0
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

}
