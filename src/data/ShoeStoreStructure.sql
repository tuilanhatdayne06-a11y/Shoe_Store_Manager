
USE ShoeStore;
GO


CREATE TABLE Employee (
    employeeId VARCHAR(20) PRIMARY KEY,
    employeeName NVARCHAR(100) NOT NULL,
    gender BIT NOT NULL,
    phoneNumber VARCHAR(15) NOT NULL,
    position NVARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,

    CONSTRAINT CK_Employee_Phone
    CHECK (
        phoneNumber LIKE '0[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'
    ),

    CONSTRAINT CK_Employee_Position
    CHECK (
        position IN (N'Quan ly', N'Nhan vien ban hang', N'Nhan vien kho')
    ),
    CONSTRAINT CK_Employee_Email
    CHECK (
        email <> ''
        AND email NOT LIKE '% %'
        AND email NOT LIKE '%@%@%'
        AND email LIKE '%_@_%._%'
        AND email NOT LIKE '%@.%'
        AND email NOT LIKE '%..%'
    )
);
GO

CREATE TABLE Account (
    employeeId VARCHAR(20) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role NVARCHAR(30) NOT NULL,

    CONSTRAINT FK_Account_Employee
        FOREIGN KEY (employeeId)
        REFERENCES Employee(employeeId)
);
GO

CREATE TABLE Customer (
    customerId VARCHAR(20) PRIMARY KEY,
    customerName NVARCHAR(100) NOT NULL,
    address NVARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(15) NOT NULL,
    loyaltyPoints INT NOT NULL DEFAULT 0,
    gender BIT NOT NULL,

    CONSTRAINT CK_Customer_LoyaltyPoints
        CHECK (loyaltyPoints >= 0),

    CONSTRAINT CK_Customer_Phone
    CHECK (
        phoneNumber LIKE '0[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'
    )
);
GO

CREATE TABLE Supplier (
    supplierId VARCHAR(20) PRIMARY KEY,
    supplierName NVARCHAR(100) NOT NULL,
    address NVARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL,
    logo NVARCHAR(255) NOT NULL,

    CONSTRAINT CK_Supplier_Phone
        CHECK (phoneNumber LIKE '0[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),

    CONSTRAINT CK_Supplier_Email
    CHECK (
        email <> ''
        AND email NOT LIKE '% %'
        AND email NOT LIKE '%@%@%'
        AND email LIKE '%_@_%._%'
        AND email NOT LIKE '%@.%'
        AND email NOT LIKE '%..%'
    )
);
GO

CREATE TABLE Category (
    categoryId VARCHAR(20) PRIMARY KEY,
    categoryName NVARCHAR(100) NOT NULL
);
GO

CREATE TABLE Product (
    productId VARCHAR(20) PRIMARY KEY,
    productName NVARCHAR(100) NOT NULL,
    unitPrice DECIMAL(18,2) NOT NULL,
    categoryId VARCHAR(20) NOT NULL,
    supplierId VARCHAR(20) NOT NULL,

    CONSTRAINT FK_Product_Category
        FOREIGN KEY (categoryId)
        REFERENCES Category(categoryId),

    CONSTRAINT FK_Product_Supplier
        FOREIGN KEY (supplierId)
        REFERENCES Supplier(supplierId),

    CONSTRAINT CK_Product_UnitPrice
        CHECK (unitPrice > 0)
);
GO

CREATE TABLE ProductDetail (
    productDetailId VARCHAR(20) PRIMARY KEY,
    size INT NOT NULL,
    color NVARCHAR(50) NOT NULL,
    stockQuantity INT NOT NULL DEFAULT 0,
    productId VARCHAR(20) NOT NULL,
    imgProductColor NVARCHAR(255) NOT NULL,

    CONSTRAINT FK_ProductDetail_Product
        FOREIGN KEY (productId)
        REFERENCES Product(productId),

    CONSTRAINT CK_ProductDetail_Stock
        CHECK (stockQuantity >= 0),

    CONSTRAINT CK_ProductDetail_Size
        CHECK (size >= 36 and size <= 46)
);
GO

CREATE TABLE PurchaseOrder (
    purchaseOrderId VARCHAR(20) PRIMARY KEY,
    orderDate DATETIME2 NOT NULL,
    employeeId VARCHAR(20) NOT NULL,
    supplierId VARCHAR(20) NOT NULL,
    status NVARCHAR(30) NOT NULL,

    CONSTRAINT FK_PurchaseOrder_Employee
        FOREIGN KEY (employeeId)
        REFERENCES Employee(employeeId),

    CONSTRAINT FK_PurchaseOrder_Supplier
        FOREIGN KEY (supplierId)
        REFERENCES Supplier(supplierId)
);
GO


CREATE TABLE PurchaseOrderDetail (
    purchaseOrderId VARCHAR(20) NOT NULL,
    productDetailId VARCHAR(20) NOT NULL,
    quantity INT NOT NULL,
    importPrice DECIMAL(18,2) NOT NULL,

    CONSTRAINT PK_PurchaseOrderDetail
        PRIMARY KEY (purchaseOrderId, productDetailId),

    CONSTRAINT FK_PurchaseOrderDetail_PurchaseOrder
        FOREIGN KEY (purchaseOrderId)
        REFERENCES PurchaseOrder(purchaseOrderId),

    CONSTRAINT FK_PurchaseOrderDetail_ProductDetail
        FOREIGN KEY (productDetailId)
        REFERENCES ProductDetail(productDetailId),

    CONSTRAINT CK_PurchaseOrderDetail_Quantity
        CHECK (quantity > 0),

    CONSTRAINT CK_PurchaseOrderDetail_ImportPrice
        CHECK (importPrice > 0)
);
GO

CREATE TABLE Invoice (
    invoiceId VARCHAR(20) PRIMARY KEY,
    creationDate DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    totalAmount DECIMAL(18,2) NOT NULL,
    employeeId VARCHAR(20) NOT NULL,
    customerId VARCHAR(20) NOT NULL,

    CONSTRAINT FK_Invoice_Employee
        FOREIGN KEY (employeeId)
        REFERENCES Employee(employeeId),

    CONSTRAINT FK_Invoice_Customer
        FOREIGN KEY (customerId)
        REFERENCES Customer(customerId),

    CONSTRAINT CK_Invoice_TotalAmount
        CHECK (totalAmount > 0)
);
GO

CREATE TABLE InvoiceDetail (
    invoiceDetailId VARCHAR(20) PRIMARY KEY,
    invoiceId VARCHAR(20) NOT NULL,
    productDetailId VARCHAR(20) NOT NULL,
    quantity INT NOT NULL,
    sellingPrice DECIMAL(18,2) NOT NULL,

    CONSTRAINT FK_InvoiceDetail_Invoice
        FOREIGN KEY (invoiceId)
        REFERENCES Invoice(invoiceId),

    CONSTRAINT FK_InvoiceDetail_ProductDetail
        FOREIGN KEY (productDetailId)
        REFERENCES ProductDetail(productDetailId),

    CONSTRAINT CK_InvoiceDetail_Quantity
        CHECK (quantity > 0),

    CONSTRAINT CK_InvoiceDetail_SellingPrice
        CHECK (sellingPrice > 0)
);

GO

CREATE TABLE IDCounter (
    prefix VARCHAR(10) PRIMARY KEY,
    currentValue INT NOT NULL
);
GO