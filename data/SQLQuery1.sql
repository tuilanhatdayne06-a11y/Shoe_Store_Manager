USE master;
GO

CREATE DATABASE ShoeStore;
GO
USE ShoeStore;
GO

CREATE TABLE Employee (
    employeeId VARCHAR(20) NOT NULL PRIMARY KEY,
    employeeName NVARCHAR(100) NOT NULL,
    gender BIT NOT NULL,
    email VARCHAR(100) NOT NULL,
    phoneNumber VARCHAR(10) NOT NULL,
    position VARCHAR(50) NOT NULL,
);


CREATE TABLE Category (
    categoryId VARCHAR(20) NOT NULL PRIMARY KEY,
    categoryName NVARCHAR(100) NOT NULL
);

CREATE TABLE Supplier (
    supplierId VARCHAR(20) NOT NULL PRIMARY KEY,
    supplierName NVARCHAR(150) NOT NULL,
    address NVARCHAR(200),
    phoneNumber VARCHAR(15),
    email VARCHAR(100),
    logo VARCHAR(255)
);
Drop Table Employee
CREATE TABLE Customer (
    customerId VARCHAR(20) NOT NULL PRIMARY KEY,
    customerName NVARCHAR(100) NOT NULL,
    address NVARCHAR(200),
    phoneNumber VARCHAR(15),
    loyaltyPoints INT DEFAULT 0
);

CREATE TABLE IDCounter (
    prefix VARCHAR(10) NOT NULL PRIMARY KEY,
    currentValue INT NOT NULL
);

CREATE TABLE Account (
    employeeId VARCHAR(20) NOT NULL PRIMARY KEY FOREIGN KEY REFERENCES Employee(employeeId),
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE Product (
    productId VARCHAR(20) NOT NULL PRIMARY KEY,
    productName NVARCHAR(150) NOT NULL,
    unitPrice FLOAT,
    categoryId VARCHAR(20),
    supplierId VARCHAR(20),
    imgPath NVARCHAR(500),
    CONSTRAINT FK_Product_Category FOREIGN KEY (categoryId) REFERENCES Category(categoryId),
    CONSTRAINT FK_Product_Supplier FOREIGN KEY (supplierId) REFERENCES Supplier(supplierId)
);

CREATE TABLE ProductDetail (
    productDetailId VARCHAR(20) NOT NULL PRIMARY KEY,
    size INT,
    color NVARCHAR(30),
    stockQuantity INT,
    productId VARCHAR(20),
    imgProductColor NVARCHAR(500),
    CONSTRAINT FK_ProductDetail_Product FOREIGN KEY (productId) REFERENCES Product(productId)
);

CREATE TABLE PurchaseOrder (
    purchaseOrderId VARCHAR(20) NOT NULL PRIMARY KEY,
    orderDate DATETIME,
    employeeId VARCHAR(20), 
    supplierId VARCHAR(20),
  
    CONSTRAINT FK_PurchaseOrder_Supplier FOREIGN KEY (supplierId) REFERENCES Supplier(supplierId)
);
  ALTER TABLE PurchaseOrder ADD status VARCHAR(20);
CREATE TABLE PurchaseOrderDetail (
    purchaseOrderId VARCHAR(20) NOT NULL,
    productDetailId VARCHAR(20) NOT NULL,
    quantity INT,
    importPrice DECIMAL(12,2),
    PRIMARY KEY (purchaseOrderId, productDetailId),
    CONSTRAINT FK_PODetail_PurchaseOrder FOREIGN KEY (purchaseOrderId) REFERENCES PurchaseOrder(purchaseOrderId),
    CONSTRAINT FK_PODetail_ProductDetail FOREIGN KEY (productDetailId) REFERENCES ProductDetail(productDetailId)
);

CREATE TABLE Invoice (
    invoiceId VARCHAR(20) NOT NULL PRIMARY KEY,
    creationDate DATETIME,
    totalAmount DECIMAL(12,2),
    employeeId VARCHAR(20), 
    customerId VARCHAR(20),
    CONSTRAINT FK_Invoice_Customer FOREIGN KEY (customerId) REFERENCES Customer(customerId)
);

CREATE TABLE InvoiceDetail (
    invoiceId VARCHAR(20) NOT NULL,
    productDetailId VARCHAR(20) NOT NULL,
    quantity INT,
    sellingPrice DECIMAL(12,2),
    PRIMARY KEY (invoiceId, productDetailId),
    CONSTRAINT FK_InvoiceDetail_Invoice FOREIGN KEY (invoiceId) REFERENCES Invoice(invoiceId),
    CONSTRAINT FK_InvoiceDetail_ProductDetail FOREIGN KEY (productDetailId) REFERENCES ProductDetail(productDetailId)
);
GO