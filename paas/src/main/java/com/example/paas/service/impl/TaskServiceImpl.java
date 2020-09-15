package com.example.paas.service.impl;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.DateUtil;
import com.example.common.util.ReturnJson;
import com.example.common.util.Tools;
import com.example.common.util.VerificationCheck;
import com.example.paas.service.MerchantService;
import com.example.paas.service.TaskService;
import com.example.paas.service.WorkerTaskService;
import com.example.mybatis.dto.TaskDto;
import com.example.mybatis.dto.TaskListDto;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.entity.Task;
import com.example.mybatis.mapper.TaskDao;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 任务表 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskDao, Task> implements TaskService {

    @Resource
    private TaskDao taskDao;
    @Resource
    private MerchantService merchantService;
    @Resource
    private WorkerTaskService workerTaskService;


    @Override
    public int count(TaskListDto taskListDto) {
        return taskDao.count(taskListDto);
    }


    @Override
    public ReturnJson selectList(TaskListDto taskListDto, RowBounds rowBounds) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        String currentUserId = "3666abe4ec7691d8c83d5b7b4d257bc9";
        Merchant merchant = merchantService.findByID(currentUserId);
        if (merchant != null) {
            rowBounds = new RowBounds(rowBounds.getOffset() * rowBounds.getLimit(), rowBounds.getLimit());
            List<Task> taskList = taskDao.selectList(taskListDto, rowBounds);
            if (taskList!=null){
                returnJson = new ReturnJson("查询成功", taskList, 200);
            }
        } else {
            returnJson = new ReturnJson("请先登录", 300);
        }
        return returnJson;
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public ReturnJson delete(Integer state, String id) {
        ReturnJson returnJson = new ReturnJson("删除失败", 300);
        if (state == 3) {
            returnJson = new ReturnJson("已完毕状态下不能删除", 300);
        } else {
            int num = taskDao.delete(id);
            if (num > 0) {
                returnJson = new ReturnJson("删除成功", 200);
            }
        }
        return returnJson;
    }


    /**
     * 添加任务
     *
     * @param taskDto
     * @return
     */
    @Override
    @Transactional
    public ReturnJson saveTask(TaskDto taskDto) {
        String currentUserId = "3666abe4ec7691d8c83d5b7b4d257bc9";
        Merchant merchant = merchantService.findByID(currentUserId);
        if (merchant != null) {
            if (taskDto.getTaskMode() == 0) {
                if (!VerificationCheck.isNull(taskDto.getMakerIds())) {
                    return new ReturnJson("必须指定创客", 300);
                }
            }
            if (Tools.str2Date(taskDto.getReleaseDate()) == null) {
                taskDto.setReleaseDate(DateUtil.getDay());
            }
            String taskCode = this.getTaskCode();
            int code = Integer.valueOf(taskCode.substring(2)) + 1;
            taskDto.setTaskCode("RW" + String.valueOf(code));
            IdentifierGenerator identifierGenerator=new DefaultIdentifierGenerator();
            taskDto.setId(identifierGenerator.nextId(new Object()).toString());
            int i = taskDao.addTask(taskDto);
            if (i > 0) {
                if (taskDto.getTaskMode() != 1) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("workerId", taskDto.getMakerIds());
                    map.put("taskId", taskDto.getId());
                    map.put("arrangePerson",merchantService.getNameById(taskDto.getMerchantId()));
                    return workerTaskService.seavWorkerTask(map);
                }
            }
            return new ReturnJson("添加失败", 300);
        } else {
            return new ReturnJson("请先登录", 300);
        }
    }

    /**
     * 查看任务详情
     *
     * @param id
     * @return
     */
    @Override
    public ReturnJson setTaskById(String id) {
        if (!VerificationCheck.isNull(id)) {
            return new ReturnJson("任务编号不能为空", 300);
        }
        Task task = taskDao.setTaskById(id);
        if (task != null) {
            return new ReturnJson("查询成功", task, 300);
        }
        return new ReturnJson("查询失败", 300);
    }


    @Override
    public String getTaskCode() {
        return taskDao.getTaskCode();
    }

    /**
     * 关单
     *
     * @param state
     * @param taskId
     * @return
     */
    @Override
    public ReturnJson close(Integer state, String taskId) {
        ReturnJson returnJson = new ReturnJson("关单失败", 300);
        if (state != 0) {
            returnJson = new ReturnJson("只有在发布中的任务才能进行关单", 300);
        } else {
            int num = taskDao.closeTask(taskId);
            if (num > 0) {
                returnJson = new ReturnJson("关单成功", 200);
            }
        }
        return returnJson;
    }


    /**
     * 重新开启任务
     *
     * @param state
     * @param taskId
     * @return
     */
    @Override
    public ReturnJson openTask(Integer state, String taskId) {
        ReturnJson returnJson = new ReturnJson("重新开启任务失败", 300);
        if (state != 1) {
            returnJson = new ReturnJson("任务必需关闭才能重新开启", 300);
        } else {
            int num = taskDao.openTask(taskId);
            if (num > 0) {
                returnJson = new ReturnJson("重新开启任务成功", 200);
            }
        }
        return returnJson;
    }

    @Override
    public ReturnJson getPlatformTaskList(TaskListDto taskListDto) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        if (taskListDto.getPageNo()==null){
            taskListDto.setPageNo(1);
        }
        RowBounds rowBounds = new RowBounds((taskListDto.getPageNo()-1) * 3,3);
        List<Task> taskList = taskDao.getPlatformTaskList(taskListDto, rowBounds);
        if (taskList!=null){
            returnJson = new ReturnJson("查询成功", taskList, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson savePlatformTask(TaskDto taskDto) {
        if (taskDto.getTaskMode() == 0) {
            if (!VerificationCheck.isNull(taskDto.getMakerIds())) {
                return new ReturnJson("必须指定创客", 300);
            }
        }
        if (Tools.str2Date(taskDto.getReleaseDate()) == null) {
            taskDto.setReleaseDate(DateUtil.getDay());
        }
        String taskCode = this.getTaskCode();
        int code = Integer.valueOf(taskCode.substring(2)) + 1;
        taskDto.setTaskCode("RW" + String.valueOf(code));
        IdentifierGenerator identifierGenerator=new DefaultIdentifierGenerator();
        taskDto.setId(identifierGenerator.nextId(new Object()).toString());
        int i = taskDao.addPlatformTask(taskDto);
        if (i > 0) {
            if (taskDto.getTaskMode() != 1) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("workerId", taskDto.getMakerIds());
                map.put("taskId", taskDto.getId());
                map.put("arrangePerson",merchantService.getNameById(taskDto.getMerchantId()));
                return workerTaskService.seavWorkerTask(map);
            }
        }
        return new ReturnJson("添加失败", 300);
    }

}
