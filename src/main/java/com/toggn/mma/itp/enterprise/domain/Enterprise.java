package com.toggn.mma.itp.enterprise.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
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

    public Enterprise(
            final String name,
            final BusinessCode business,
            final EnterpriseScaleCode scale,
            final String websiteUrl,
            final String address
    ) {
        this.name = name;
        this.business = business;
        this.scale = scale;
        this.websiteUrl = websiteUrl;
        this.address = address;
    }
}
