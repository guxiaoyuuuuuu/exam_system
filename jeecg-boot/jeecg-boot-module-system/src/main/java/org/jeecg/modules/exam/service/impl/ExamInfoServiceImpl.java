package org.jeecg.modules.exam.service.impl;

import org.jeecg.modules.exam.entity.ExamInfo;
import org.jeecg.modules.exam.mapper.ExamInfoMapper;
import org.jeecg.modules.exam.service.IExamInfoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 考试须知表
 * @Author: jeecg-boot
 * @Date:   2020-04-15
 * @Version: V1.0
 */
@Service
public class ExamInfoServiceImpl extends ServiceImpl<ExamInfoMapper, ExamInfo> implements IExamInfoService {

}
