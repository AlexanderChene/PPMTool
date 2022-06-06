package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.Backlog;
import io.agileintelligence.ppmtool.domain.ProjectTask;
import io.agileintelligence.ppmtool.repositories.BacklogRepository;
import io.agileintelligence.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;



    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        //PTs to be added to a specific project, project !==null, BL exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        // set backlog to project task
        projectTask.setBacklog(backlog);
        Integer BacklogSequence = backlog.getPTSquence();
        BacklogSequence++;

        backlog.setPTSquence(BacklogSequence);
        //update backlog sequence
        projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);
        //initial priority when priority null

        if( projectTask.getPriority() == null){
            projectTask.setPriority(3);
        }
        //initial status when status is null
        if(projectTask.getStatus() =="" || projectTask.getStatus() == null){
            projectTask.setStatus("TODO");
        }

        return projectTaskRepository.save(projectTask);
    }
}
