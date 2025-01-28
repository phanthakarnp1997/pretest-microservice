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
