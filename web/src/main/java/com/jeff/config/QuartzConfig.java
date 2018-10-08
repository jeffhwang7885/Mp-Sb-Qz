package com.jeff.config;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.*;

import java.io.IOException;
import java.util.*;

@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class QuartzConfig {

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    //Inject Multiple triggers/jobs (Spring setting)
    @Bean
    public SchedulerFactoryBean scheduler(List<Trigger> triggers, JobFactory jobFactory, Properties quartzProperties) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setAutoStartup(true);
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(quartzProperties);

        // Here we will set all the trigger beans we have defined.
        if (!triggers.isEmpty()) {
            factory.setTriggers(triggers.toArray(new Trigger[triggers.size()]));
        }

        return factory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }


    //Inject Single job
//    @Bean
//    public SchedulerFactoryBean scheduler(JobDetail job, Trigger trigger) {
//        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
//        schedulerFactory.setConfigLocation(new ClassPathResource("quartz/quartz.properties"));
//        schedulerFactory.setJobFactory(springBeanJobFactory());
//        schedulerFactory.setJobDetails(job);
//        schedulerFactory.setTriggers(trigger);
//        return schedulerFactory;
//    }

    //Inject Multiple triggers/jobs (Quartz setting)
//    @Bean
//    public Scheduler scheduler(Map<String, JobDetail> jobMap, List<Trigger> triggers) throws SchedulerException, IOException {
//        StdSchedulerFactory factory = new StdSchedulerFactory();
//        factory.initialize(new ClassPathResource("quartz/quartz.properties").getInputStream());
//        Scheduler scheduler = factory.getScheduler();
//        scheduler.setJobFactory(springBeanJobFactory());
//        Map<JobDetail,Set<? extends Trigger>> triggersAndJobs = new HashMap<>();
//        for(JobDetail jobDetail : jobMap.values()){
//            for(Trigger trigger : triggers){
//                if(trigger.getJobKey().equals(jobDetail.getKey())){
//                    Set<Trigger> set = new HashSet<>();
//                    set.add(trigger);
//                    triggersAndJobs.put(jobDetail,set);
//                }
//            }
//        }
//        scheduler.scheduleJobs(triggersAndJobs, false);
//        scheduler.start();
//        return scheduler;
//    }

//    @Bean
//    public JobDetail jobDetail() {
//        return JobBuilder.newJob().ofType(SampleJob.class)
//                .storeDurably()
//                .withIdentity("Qrtz_Job_Detail")
//                .withDescription("Invoke Sample Job service...")
//                .build();
//    }

//    @Bean
//    public Trigger trigger(JobDetail job) {
//        return TriggerBuilder.newTrigger().forJob(job)
//                .withIdentity("Qrtz_Trigger")
//                .withDescription("Sample trigger")
//                .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(10))
//                .build();
//    }


    public static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, long pollFrequencyMs) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setRepeatInterval(pollFrequencyMs);
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        // in case of misfire, ignore all missed triggers and continue :
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
        return factoryBean;
    }

    // Use this method for creating cron triggers instead of simple triggers:
    public static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression(cronExpression);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        return factoryBean;
    }

    public static JobDetailFactoryBean createJobDetail(Class jobClass) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        // job has to be durable to be stored in DB:
//        factoryBean.setDurability(true);
        return factoryBean;
    }


}
