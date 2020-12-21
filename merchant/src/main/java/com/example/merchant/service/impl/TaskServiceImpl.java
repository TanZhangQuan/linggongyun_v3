package com.example.merchant.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.util.*;
import com.example.merchant.dto.makerend.QueryMissionHall;
import com.example.merchant.dto.merchant.AddTaskDto;
import com.example.merchant.service.WorkerTaskService;
import com.example.mybatis.dto.PlatformTaskDTO;
import com.example.mybatis.dto.TaskDTO;
import com.example.mybatis.dto.TaskListDTO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.merchant.service.TaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.vo.TaskInfoVO;
import com.example.mybatis.vo.WorkerTaskVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private MerchantDao merchantDao;
    @Resource
    private WorkerTaskService workerTaskService;
    @Resource
    private WorkerTaskDao workerTaskDao;
    @Resource
    private WorkerDao workerDao;
    @Resource
    private IndustryDao industryDao;


    @Override
    public ReturnJson selectList(TaskListDTO taskListDto, String userId) {
        Page page = new Page(taskListDto.getPageNo(), taskListDto.getPageSize());
        IPage<Task> taskList = taskDao.selectLists(page, taskListDto, userId);
        return ReturnJson.success(taskList);
    }

    @Override
    public ReturnJson delete(String id) {
        Task task = taskDao.selectById(id);
        if ("3".equals(task.getState())) {
            ReturnJson.error("已完毕状态下不能删除");
        }
        workerTaskDao.delete(new QueryWrapper<WorkerTask>().eq("task_id", task.getId()));
        int num = taskDao.delete(id);
        return ReturnJson.success("操作成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson saveTask(AddTaskDto addTaskDto, String userId) {
        Merchant merchant = merchantDao.selectById(userId);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter df1 = DateTimeFormatter.ofPattern("HH:mm:ss");
        if (merchant == null) {
            return ReturnJson.error("商户不存在！");
        }
        if (addTaskDto.getTaskCostMax().compareTo(addTaskDto.getTaskCostMax()) == -1) {
            return ReturnJson.error("结束金额必须大于起始金额");
        }
        Task task = taskDao.selectById(addTaskDto.getId());
        if (task != null) {
            BeanUtils.copyProperties(addTaskDto, task);
            task.setReleaseDate(LocalDate.parse(addTaskDto.getReleaseDate(), df));
            task.setReleaseTime(LocalTime.parse(addTaskDto.getReleaseTime(), df1));
            task.setDeadlineDate(LocalDate.parse(addTaskDto.getDeadlineDate(), df));
            task.setDeadlineTime(LocalTime.parse(addTaskDto.getDeadlineTime(), df1));
            taskDao.updateById(task);
            return ReturnJson.success("修改成功");
        } else {
            task = new Task();
            BeanUtils.copyProperties(addTaskDto, task);
            task.setMerchantId(merchant.getId());
            task.setMerchantName(merchant.getCompanyName());
            if (addTaskDto.getTaskMode() == 0) {
                if (!VerificationCheck.isNull(addTaskDto.getMakerIds())) {
                    return new ReturnJson("必须指定创客", 300);
                }
                if (addTaskDto.getUpperLimit() < Arrays.asList(addTaskDto.getMakerIds().split(",")).size()) {
                    return ReturnJson.error("派单任务人数不能超过上线人数");
                }
            }
            task.setReleaseDate(LocalDate.parse(addTaskDto.getReleaseDate(), df));
            task.setReleaseTime(LocalTime.parse(addTaskDto.getReleaseTime(), df1));
            task.setDeadlineDate(LocalDate.parse(addTaskDto.getDeadlineDate(), df));
            task.setDeadlineTime(LocalTime.parse(addTaskDto.getDeadlineTime(), df1));
            String taskCode = this.getTaskCode();
            int code = Integer.valueOf(taskCode.substring(2)) + 1;
            task.setTaskCode("RW" + code);
            int i = taskDao.insert(task);
            if (i > 0) {
                if (addTaskDto.getTaskMode() != 1) {
                    Map<String, String> map = new HashMap<>();
                    map.put("workerId", addTaskDto.getMakerIds());
                    map.put("taskId", task.getId());
                    map.put("arrangePerson", merchantDao.getNameById(merchant.getId()));
                    workerTaskService.seavWorkerTask(map);
                }
            }
            return ReturnJson.success("添加成功！");
        }
    }

    @Override
    public ReturnJson setTaskById(String id) {
        if (!VerificationCheck.isNull(id)) {
            return ReturnJson.error("请选择任务");
        }
        Task task = taskDao.setTaskById(id);
        return ReturnJson.success(task);
    }

    @Override
    public String getTaskCode() {
        return taskDao.getTaskCode();
    }

    @Override
    public ReturnJson close(String taskId) {
        Task task = taskDao.selectById(taskId);
        if (task.getState() != 0) {
            return ReturnJson.error("只有在发布中的任务才能进行关单");
        } else {
            int num = taskDao.closeTask(taskId);
            return ReturnJson.success("关单成功");
        }
    }

    @Override
    public ReturnJson openTask(String taskId) {
        Task task = taskDao.selectById(taskId);
        if (task.getState() == 1) {
            task.setState(0);
            taskDao.updateById(task);
            return ReturnJson.success("操作成功");
        } else {
            return ReturnJson.error("任务必需关闭才能重新开启");
        }
    }

    @Override
    public ReturnJson getPlatformTaskList(PlatformTaskDTO platformTaskDto) {
        Page page = new Page(platformTaskDto.getPageNo(), platformTaskDto.getPageSize());
        IPage<Task> taskList = taskDao.getPlatformTaskList(page, platformTaskDto);
        return ReturnJson.success(taskList);
    }

    @Override
    public ReturnJson savePlatformTask(TaskDTO taskDto) {
        Merchant merchant = merchantDao.selectById(taskDto.getMerchantId());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter df1 = DateTimeFormatter.ofPattern("HH:mm:ss");
        Task task = new Task();
        if (merchant == null) {
            return ReturnJson.error("商户不存在！");
        }
        if (taskDto.getTaskMode() == 0) {
            if (!VerificationCheck.isNull(taskDto.getMakerIds())) {
                return new ReturnJson("必须指定创客", 300);
            }
        }
        BeanUtils.copyProperties(taskDto, task);
        task.setReleaseDate(LocalDate.parse(taskDto.getReleaseDate(), df));
        task.setReleaseTime(LocalTime.parse(taskDto.getReleaseTime(), df1));
        task.setDeadlineDate(LocalDate.parse(taskDto.getDeadlineDate(), df));
        task.setDeadlineTime(LocalTime.parse(taskDto.getDeadlineTime(), df1));
        String taskCode = this.getTaskCode();
        int code = Integer.valueOf(taskCode.substring(2)) + 1;
        task.setTaskCode("RW" + String.valueOf(code));
        int i = taskDao.insert(task);
        if (i > 0) {
            if (taskDto.getTaskMode() != 1) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("workerId", taskDto.getMakerIds());
                map.put("taskId", task.getId());
                map.put("arrangePerson", merchantDao.getNameById(taskDto.getMerchantId()));
                return workerTaskService.seavWorkerTask(map);
            }
            return ReturnJson.success("添加成功");
        }
        return new ReturnJson("添加失败", 300);
    }

    @Override
    public ReturnJson updatePlatfromTask(TaskDTO taskDto) {
        if (taskDto.getId() == null) {
            return ReturnJson.error("请选择任务");
        }
        Task task = taskDao.selectById(taskDto.getId());
        if (task == null) {
            return ReturnJson.error("你选择的任务不存在，请选择正确的任务");
        }
        BeanUtil.copyProperties(task, taskDto);
        taskDao.updateById(task);
        return ReturnJson.success("编辑成功");
    }

    @Override
    public ReturnJson setTask(QueryMissionHall queryMissionHall) {
        Page page = new Page(queryMissionHall.getPageNo(), queryMissionHall.getPageSize());
        IPage<TaskInfoVO> list = taskDao.setTask(page, queryMissionHall.getIndustryType());
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson taskDetails(String taskId) {
        Task task = taskDao.selectById(taskId);
        return ReturnJson.success(task);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized ReturnJson orderGrabbing(String taskId, String workerId) {
        Worker worker = workerDao.selectOne(new QueryWrapper<Worker>().eq("id", workerId));
        if (worker.getAttestation() != 1) {
            return ReturnJson.error("您还未实名,请前往实名认证！");
        }
        if (worker.getAgreementSign() != 2) {
            return ReturnJson.error("您还未签约,请前往签约！");
        }
        Task task = taskDao.selectById(taskId);
        int count = workerTaskDao.selectCount(new QueryWrapper<WorkerTask>().eq("task_id", taskId).eq("task_status", 0));
        //用户是否已经抢过这一单
        int count1 = workerTaskDao.selectCount(new QueryWrapper<WorkerTask>().eq("task_id", taskId).eq("worker_id", workerId));
        if (count1 > 0) {
            return ReturnJson.error("您已经抢过这一单了！");
        }
        if (count >= task.getUpperLimit()) {
            return ReturnJson.error("任务所需人数已满！");
        }
        WorkerTask workerTask = new WorkerTask();
        workerTask.setTaskId(taskId);
        workerTask.setWorkerId(workerId);
        workerTask.setTaskStatus(0);
        workerTask.setGetType(0);
        workerTask.setStatus(0);
        workerTask.setCreateDate(LocalDateTime.now());
        workerTaskDao.insert(workerTask);
        count = workerTaskDao.selectCount(new QueryWrapper<WorkerTask>().eq("task_id", taskId).eq("task_status", 0));
        if (count == task.getUpperLimit()) {
            task.setState(1);
            taskDao.updateById(task);
        }
        return ReturnJson.success("恭喜,抢单成功！");
    }

    @Override
    public ReturnJson myTask(String workerId, String status) {
        List<WorkerTaskVO> workerTaskList = workerTaskDao.myTask(workerId, status);
        return ReturnJson.success(workerTaskList);
    }


    @Override
    public ReturnJson getindustryType() {
        List<String> list = industryDao.getIndustryType();
        return ReturnJson.success(list);
    }

}
