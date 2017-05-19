package models;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(Computer.class)
public class Computer_ {
    public static volatile SingularAttribute<Computer, Integer> id;
    public static volatile SingularAttribute<Computer, String> name;
    public static volatile SingularAttribute<Computer, LocalDateTime> introduced;
    public static volatile SingularAttribute<Computer, LocalDateTime> discontinued;
    public static volatile SingularAttribute<Computer, Company> company;
}