package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.util.*;
import com.example.merchant.service.MerchantService;
import com.example.merchant.service.WorkerTaskService;
import com.example.mybatis.dto.PlatformTaskDto;
import com.example.mybatis.dto.TaskDto;
import com.example.mybatis.dto.TaskListDto;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.entity.Task;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.entity.WorkerTask;
import com.example.mybatis.mapper.MerchantDao;
import com.example.mybatis.mapper.TaskDao;
import com.example.merchant.service.TaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.mapper.WorkerDao;
import com.example.mybatis.mapper.WorkerTaskDao;
import com.example.mybatis.vo.WorkerTaskVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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


    @Override
    public int count(TaskListDto taskListDto) {
        return taskDao.count(taskListDto);
    }


    /**
     * 任务列表
     *
     * @param taskListDto
     * @param rowBounds
     * @return
     */
    @Override
    public ReturnJson selectList(TaskListDto taskListDto, RowBounds rowBounds) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        rowBounds = new RowBounds((rowBounds.getOffset() - 1) * rowBounds.getLimit(), rowBounds.getLimit());
        List<Task> taskList = taskDao.selectLists(taskListDto, rowBounds);
        if (taskList != null) {
            returnJson = new ReturnJson("查询成功", taskList, 200);
        }
        returnJson.setPageSize(taskListDto.getPageSize());
        returnJson.setItemsCount(taskListDto.getPageNo());
        return returnJson;
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
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


    /**
     * 添加任务
     *
     * @param taskDto
     * @return
     */
    @Override
    @Transactional
    public ReturnJson saveTask(TaskDto taskDto) {
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
        IdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();
        taskDto.setId(identifierGenerator.nextId(new Object()).toString());
        int i = taskDao.addTask(taskDto);
        if (i > 0) {
            if (taskDto.getTaskMode() != 1) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("workerId", taskDto.getMakerIds());
                map.put("taskId", taskDto.getId());
                map.put("arrangePerson", merchantDao.getNameById(taskDto.getMerchantId()));
                return workerTaskService.seavWorkerTask(map);
            }
        }
        return new ReturnJson("添加失败", 300);
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
            return ReturnJson.error("任务ID不能为空");
        }
        Task task = taskDao.setTaskById(id);
        return ReturnJson.success(task);
    }


    @Override
    public String getTaskCode() {
        return taskDao.getTaskCode();
    }

    /**
     * 关单
     *
     * @param taskId
     * @return
     */
    @Override
    public ReturnJson close(String taskId) {
        Task task = taskDao.selectById(taskId);
        if (task.getState() == 0) {
            return ReturnJson.error("只有在发布中的任务才能进行关单");
        } else {
            int num = taskDao.closeTask(taskId);
            return ReturnJson.success("关单成功");
        }
    }


    /**
     * 重新开启任务
     *
     * @param taskId
     * @return
     */
    @Override
    public ReturnJson openTask(String taskId) {
        Task task = taskDao.selectById(taskId);
        if (task.getState() == 1) {
            int num = taskDao.openTask(taskId);
            return ReturnJson.success("操作成功");
        } else {
            return ReturnJson.error("任务必需关闭才能重新开启");
        }
    }

    /**
     * 平台任务列表
     *
     * @param platformTaskDto
     * @return
     */
    @Override
    public ReturnJson getPlatformTaskList(PlatformTaskDto platformTaskDto) {
        IPage<Task> taskList = taskDao.getPlatformTaskList(platformTaskDto, new Page(platformTaskDto.getPageNo(),platformTaskDto.getPageSize()));
        return ReturnJson.success(taskList);
    }

    /**
     * 平台任务添加
     *
     * @param taskDto
     * @return
     */
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
        IdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();
        taskDto.setId(identifierGenerator.nextId(new Object()).toString());
        int i = taskDao.addPlatformTask(taskDto);
        if (i > 0) {
            if (taskDto.getTaskMode() != 1) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("workerId", taskDto.getMakerIds());
                map.put("taskId", taskDto.getId());
                map.put("arrangePerson", merchantDao.getNameById(taskDto.getMerchantId()));
                return workerTaskService.seavWorkerTask(map);
            }
        }
        return new ReturnJson("添加失败", 300);
    }

    /**
     * 小程序任务大厅
     *
     * @param industryType
     * @return
     */
    @Override
    public ReturnJson setTask(String merchantId, String industryType) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        List<Task> list = taskDao.setTask(merchantId, industryType);
        if (list != null) {
            returnJson = new ReturnJson("操作成功", list, 200);
        }
        return returnJson;
    }

    /**
     * 查询任务详情
     *
     * @param taskId
     * @return
     */
    @Override
    public ReturnJson taskDetails(String taskId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        Task task = taskDao.selectById(taskId);
        if (task != null) {
            returnJson = new ReturnJson("操作成功", task, 200);
        }
        return returnJson;
    }

    /**
     * 抢单
     *
     * @param taskId
     * @param workerId
     * @return
     */
    @Override
    @Transactional
    public ReturnJson orderGrabbing(String taskId, String workerId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        Worker worker = workerDao.selectOne(new QueryWrapper<Worker>().eq("id", workerId));
        if (worker.getAttestation() != 1 && worker.getAgreementSign() != 1) {
            return returnJson.error("您还不是认证用户");
        }
        Task task = taskDao.selectById(taskId);
        int count = workerTaskDao.selectCount(new QueryWrapper<WorkerTask>().eq("task_id", taskId).eq("task_status", 0));
        int count1 = workerTaskDao.selectCount(new QueryWrapper<WorkerTask>().eq("task_id", taskId).eq("worker_id", workerId));//用户是否已经抢过这一单
        if (count1 > 0) {
            return returnJson.error("您已经抢过这一单了");
        }
        synchronized (this) {
            if (count >= task.getUpperLimit()) {
                return returnJson.error("任务所需人数已满");
            }
            WorkerTask workerTask = new WorkerTask();
            workerTask.setTaskId(taskId);
            workerTask.setWorkerId(workerId);
            workerTask.setTaskStatus(0);
            workerTask.setGetType(0);
            workerTask.setCreateDate(LocalDateTime.now());
            int num = workerTaskDao.insert(workerTask);
            if (num > 0) {
                return returnJson.error("恭喜,抢单成功");
            }
        }
        return returnJson;
    }

    /**
     * 我的任务,某个创客的所有任务
     *
     * @param workerId 创客id
     * @param status   任务状态
     * @return
     */
    @Override
    public ReturnJson myTask(String workerId, String status) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        List<WorkerTaskVo> workerTaskList = workerTaskDao.myTask(workerId, status);
        if (workerTaskList != null) {
            returnJson = new ReturnJson("操作成功", workerTaskList, 200);
        }
        return returnJson;
    }

}
