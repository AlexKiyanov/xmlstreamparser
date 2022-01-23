package ru.kiianov.xmlstreamparser.dao.entity;

import java.util.Objects;

public final class CompanyEntity {
    private final Long id;
    private final String name;
    private final String domain;
    private final String yearFounded;
    private final String industry;
    private final String sizeRange;
    private final String locality;
    private final String country;
    private final String linkedinUrl;
    private final Long currentEmployeeEstimate;
    private final Long totalEmployeeEstimate;

    private CompanyEntity(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.domain = builder.domain;
        this.yearFounded = builder.yearFounded;
        this.industry = builder.industry;
        this.sizeRange = builder.sizeRange;
        this.locality = builder.locality;
        this.country = builder.country;
        this.linkedinUrl = builder.linkedinUrl;
        this.currentEmployeeEstimate = builder.currentEmployeeEstimate;
        this.totalEmployeeEstimate = builder.totalEmployeeEstimate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDomain() {
        return domain;
    }

    public String getYearFounded() {
        return yearFounded;
    }

    public String getIndustry() {
        return industry;
    }

    public String getSizeRange() {
        return sizeRange;
    }

    public String getLocality() {
        return locality;
    }

    public String getCountry() {
        return country;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public Long getCurrentEmployeeEstimate() {
        return currentEmployeeEstimate;
    }

    public Long getTotalEmployeeEstimate() {
        return totalEmployeeEstimate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyEntity that = (CompanyEntity) o;
        return id.equals(that.id)
                && name.equals(that.name)
                && Objects.equals(domain, that.domain)
                && Objects.equals(yearFounded, that.yearFounded)
                && Objects.equals(industry, that.industry)
                && Objects.equals(sizeRange, that.sizeRange)
                && Objects.equals(locality, that.locality)
                && Objects.equals(country, that.country)
                && Objects.equals(linkedinUrl, that.linkedinUrl)
                && Objects.equals(currentEmployeeEstimate, that.currentEmployeeEstimate)
                && Objects.equals(totalEmployeeEstimate, that.totalEmployeeEstimate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                name,
                domain,
                yearFounded,
                industry,
                sizeRange,
                locality,
                country,
                linkedinUrl,
                currentEmployeeEstimate,
                totalEmployeeEstimate);
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", domain='" + domain + '\'' +
                ", yearFounded='" + yearFounded + '\'' +
                ", industry='" + industry + '\'' +
                ", sizeRange='" + sizeRange + '\'' +
                ", locality='" + locality + '\'' +
                ", country='" + country + '\'' +
                ", linkedinUrl='" + linkedinUrl + '\'' +
                ", currentEmployeeEstimate=" + currentEmployeeEstimate +
                ", totalEmployeeEstimate=" + totalEmployeeEstimate +
                '}' + "\n";
    }

    public static final class Builder {
        private Long id;
        private String name;
        private String domain;
        private String yearFounded;
        private String industry;
        private String sizeRange;
        private String locality;
        private String country;
        private String linkedinUrl;
        private Long currentEmployeeEstimate;
        private Long totalEmployeeEstimate;

        private Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDomain(String domain) {
            this.domain = domain;
            return this;
        }

        public Builder withYearFounded(String yearFounded) {
            this.yearFounded = yearFounded;
            return this;
        }

        public Builder withIndustry(String industry) {
            this.industry = industry;
            return this;
        }

        public Builder withSizeRange(String sizeRange) {
            this.sizeRange = sizeRange;
            return this;
        }

        public Builder withLocality(String locality) {
            this.locality = locality;
            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder withLinkedinUrl(String linkedinUrl) {
            this.linkedinUrl = linkedinUrl;
            return this;
        }

        public Builder withCurrentEmployeeEstimate(Long currentEmployeeEstimate) {
            this.currentEmployeeEstimate = currentEmployeeEstimate;
            return this;
        }

        public Builder withTotalEmployeeEstimate(Long totalEmployeeEstimate) {
            this.totalEmployeeEstimate = totalEmployeeEstimate;
            return this;
        }

        public CompanyEntity build() {
            return new CompanyEntity(this);
        }
    }
}
