-- Delete customer_issue Table
IF OBJECT_ID(N'[dbo].[customer_issue]', 'U') IS NOT NULL
BEGIN
    DROP TABLE dbo.customer_issue
END;

-- Create customer_issue Table
IF OBJECT_ID(N'[dbo].[customer_issue]', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.customer_issue (
        issue_id INT PRIMARY KEY IDENTITY(1,1),
        description VARCHAR(50) NOT NULL,
        forward_to VARCHAR(50) NOT NULL,
        status VARCHAR(20) NOT NULL,
        created_on DATETIME DEFAULT GETDATE(),
        modifier_on DATETIME DEFAULT GETDATE()
    )
END;


IF OBJECT_ID(N'[dbo].[routing_rules]', 'U') IS NOT NULL
BEGIN
    DROP TABLE dbo.routing_rules
END;

IF OBJECT_ID(N'[dbo].[routing_rules]', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.routing_rules (
        request_type VARCHAR(50) PRIMARY KEY ,
        service_url VARCHAR(50) NOT NULL,
    )
END;

INSERT INTO dbo.routing_rules (request_type, service_url)
VALUES
('credit_card', 'http://credit-card-service/api/v1/issue'),
('account', 'http://account-service/api/v1/issue'),
('loan', 'http://loan-service/api/v1/issue');