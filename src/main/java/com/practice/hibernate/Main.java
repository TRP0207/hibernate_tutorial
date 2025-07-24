package com.practice.hibernate;

import com.practice.hibernate.model.Developer;
import com.practice.hibernate.model.DeveloperName;
import com.practice.hibernate.model.Project;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Project project1 = new Project();
        project1.setProjectId(1);
        project1.setProjectName("Project1");

        Project project2 = new Project();
        project2.setProjectId(2);
        project2.setProjectName("Project2");

        List<Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);

        DeveloperName developerName1 = new DeveloperName();
        developerName1.setFname("Rahul");
        developerName1.setLname("Parghi");
        developerName1.setMname("N.");

        Developer developer1 = new Developer();
        developer1.setId(101);
        developer1.setName(developerName1);
        developer1.setTech("Java");
        developer1.setProjects(projects);

        DeveloperName developerName2 = new DeveloperName();
        developerName2.setFname("Jaydeep");
        developerName2.setLname("Chauhan");
        developerName2.setMname("J.");

        Developer developer2 = new Developer();
        developer2.setId(102);
        developer2.setTech("Cloud");
        developer2.setName(developerName2);
        developer2.setProjects(projects);

        SessionFactory sessionFactory = new Configuration()
                .addAnnotatedClass(Developer.class)
                .addAnnotatedClass(Project.class)
                .configure()
                .buildSessionFactory();

        // Open Session
        Session session = sessionFactory.openSession();

        // Begin Transaction
        Transaction transaction = session.beginTransaction();

        session.persist(project1);
        session.persist(project2);

        session.persist(developer1);
        session.persist(developer2);

        // Commit transaction
        transaction.commit();

        // Close Session and Session Factory
        session.close();

        System.out.println("\n------------------------- First Level Caching -------------------------\n");

        Session sessionFirstLvlCache = sessionFactory.openSession();

        Developer developerCache1 = sessionFirstLvlCache.find(Developer.class, 101);
        System.out.println(developerCache1);

        Developer developerCache2 = sessionFirstLvlCache.find(Developer.class, 101);
        System.out.println(developerCache2);

        sessionFirstLvlCache.close();

        System.out.println("\n------------------------- Second Level Caching -------------------------\n");

        Session secondLvlCacheSession1 = sessionFactory.openSession();
        Session secondLvlCacheSession2 = sessionFactory.openSession();

        Developer developerOfSecondLvlCacheSession1 = secondLvlCacheSession1.find(Developer.class, 102);
        System.out.println(developerOfSecondLvlCacheSession1);

        Developer developerOfSecondLvlCacheSession2 = secondLvlCacheSession2.find(Developer.class, 102);
        System.out.println(developerOfSecondLvlCacheSession2);

        System.out.println("\n------------------------- Second Level Caching with Query-------------------------\n");

        Query<Developer> q1 = secondLvlCacheSession1.createQuery("from Developer where id=102");
        q1.setCacheable(true);
        Developer developerOfSecondLvlCacheSession1WithQuery1 = q1.uniqueResult();
        System.out.println(developerOfSecondLvlCacheSession1WithQuery1);

        Query q2 = secondLvlCacheSession1.createQuery("from Developer where id=102");
        q2.setCacheable(true);
        Developer developerOfSecondLvlCacheSession1WithQuery2 = (Developer) q1.uniqueResult();
        System.out.println(developerOfSecondLvlCacheSession1WithQuery2);

        secondLvlCacheSession1.close();
        secondLvlCacheSession2.close();

        System.out.println("\n------------------------- HQL -------------------------\n");

        Session sessionForHQL = sessionFactory.openSession();
        Query hqlQ1 = sessionForHQL.createQuery("from Developer");
        List<Developer> devList = hqlQ1.list();

        for (Developer dev : devList)
            System.out.println(dev);

        //Query hqlQ2 = sessionForHQL.createQuery("select name from Developer where id=102");
        Query hqlQ2 = sessionForHQL.createQuery("select name from Developer where id= :id");
        hqlQ2.setParameter("id",102);
        List<DeveloperName> devNameList = hqlQ2.list();

        for (DeveloperName devName : devNameList)
            System.out.println(devName);

        sessionFactory.close();
    }
}