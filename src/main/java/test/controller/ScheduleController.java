package test.controller;

import org.quartz.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.pojo.common.SampleJob;

/**
 * quartz 测试
 */
@RestController
@RequestMapping("/api/job")
public class ScheduleController {
    private Scheduler scheduler;

    public ScheduleController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PutMapping("")
    public void add() throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(SampleJob.class).withIdentity(JobKey.jobKey("Test1")).build();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ?")).withIdentity("Test1").build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    @DeleteMapping("")
    public void deleteAll() throws SchedulerException{
        scheduler.deleteJob(JobKey.jobKey("Test1"));
    }
}
