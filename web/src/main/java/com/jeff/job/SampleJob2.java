package com.jeff.job;

import com.jeff.config.QuartzConfig;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class SampleJob2 implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Sample 2!!!!!");
    }

    @Bean(name="sample2Job")
    public JobDetailFactoryBean sampleJob2() {
        return QuartzConfig.createJobDetail(this.getClass());
    }

    @Bean(name="sample2Trigger")
    public SimpleTriggerFactoryBean trigger2(@Qualifier("sample2Job") JobDetail jobDetail) {
        return QuartzConfig.createTrigger(jobDetail, 5000);
    }
}
