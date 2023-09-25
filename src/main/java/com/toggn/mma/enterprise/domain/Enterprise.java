package com.toggn.mma.enterprise.domain;

import jakarta.persistence.*;

@Entity
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_code", length = 20, nullable = false)
    private BusinessCode business;

    @Enumerated(EnumType.STRING)
    @Column(name = "scale_code", length = 20, nullable = false)
    private EnterpriseScaleCode scale;

    @Column(length = 1024, nullable = false)
    private String websiteUrl;

    @Column(length = 1024, nullable = false)
    private String address;
}
