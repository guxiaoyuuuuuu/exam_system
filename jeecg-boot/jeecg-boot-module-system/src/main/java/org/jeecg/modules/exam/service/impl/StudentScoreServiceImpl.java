package org.jeecg.modules.exam.service.impl;

import org.jeecg.modules.exam.entity.StudentScore;
import org.jeecg.modules.exam.mapper.StudentScoreMapper;
import org.jeecg.modules.exam.service.IStudentScoreService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 学生成绩表
 * @Author: jeecg-boot
 * @Date:   2020-04-18
 * @Version: V1.0
 */
@Service
public class StudentScoreServiceImpl extends ServiceImpl<StudentScoreMapper, StudentScore> implements IStudentScoreService {

}
