package org.jeecg.modules.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 考试安排表
 * @Author: jeecg-boot
 * @Date:   2020-04-15
 * @Version: V1.0
 */
@Data
@TableName("exam_arrange")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="exam_arrange对象", description="考试安排表")
public class ExamArrangeVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private String id;
	/**科目编号*/
	@Excel(name = "科目编号", width = 15)
    @ApiModelProperty(value = "科目编号")
    private String courseNo;
    /**科目名称*/
    @Excel(name = "科目名称", width = 15)
    @ApiModelProperty(value = "科目名称")
    private String courseName;
	/**教室考场号*/
	@Excel(name = "教室考场号", width = 15)
    @ApiModelProperty(value = "教室考场号")
    private String examNo;
    /**教室名称*/
    @Excel(name = "教室名称", width = 15)
    @ApiModelProperty(value = "教室名称")
    private String name;
	/**考试批次号*/
	@Excel(name = "考试批次号", width = 15)
    @ApiModelProperty(value = "考试批次号")
    private String batchNo;
	/**教师编号*/
	@Excel(name = "教师编号", width = 15)
    @ApiModelProperty(value = "教师编号")
    private String teacherNo;
    /**教师名称*/
    @Excel(name = "教师名称", width = 15)
    @ApiModelProperty(value = "教师名称")
    private String realname;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
}
