DROP TABLE IF EXISTS companies;

CREATE TABLE companies (
   company_id INT NOT NULL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   company_domain VARCHAR(200),
   yearFounded VARCHAR(10),
   industry VARCHAR(100),
   sizeRange VARCHAR(50),
   locality VARCHAR(100),
   country VARCHAR(50),
   linkedinUrl VARCHAR(255),
   currentEmployeeEstimate INT,
   totalEmployeeEstimate INT
)
