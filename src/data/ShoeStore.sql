
-- ShoeStore database schema and sample data for Microsoft SQL Server.
-- Demo accounts: NV002-NV009 / 123456 and NV010 / 123456.
-- Change these sample passwords before using the database outside local development.

IF DB_ID(N'ShoeStore') IS NULL
    CREATE DATABASE [ShoeStore];
GO

USE [ShoeStore];
GO

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID(N'dbo.Employee', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Employee (
        employeeId varchar(20) NOT NULL CONSTRAINT PK_Employee PRIMARY KEY,
        employeeName nvarchar(100) NOT NULL,
        gender bit NOT NULL,
        email varchar(100) NOT NULL,
        phoneNumber varchar(15) NOT NULL,
        position nvarchar(50) NOT NULL
    );
END;
GO

IF OBJECT_ID(N'dbo.Account', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Account (
        employeeId varchar(20) NOT NULL CONSTRAINT PK_Account PRIMARY KEY,
        [password] varchar(255) NOT NULL,
        [role] nvarchar(50) NOT NULL,
        CONSTRAINT FK_Account_Employee FOREIGN KEY (employeeId)
            REFERENCES dbo.Employee(employeeId) ON DELETE CASCADE
    );
END;
GO

IF OBJECT_ID(N'dbo.Category', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Category (
        categoryId varchar(20) NOT NULL CONSTRAINT PK_Category PRIMARY KEY,
        categoryName nvarchar(100) NOT NULL
    );
END;
GO

IF OBJECT_ID(N'dbo.Customer', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Customer (
        customerId varchar(20) NOT NULL CONSTRAINT PK_Customer PRIMARY KEY,
        customerName nvarchar(100) NOT NULL,
        address nvarchar(200) NULL,
        phoneNumber varchar(15) NULL,
        loyaltyPoints int NOT NULL CONSTRAINT DF_Customer_loyaltyPoints DEFAULT (0),
        imgCustomer nvarchar(500) NULL
    );
END;
GO

IF OBJECT_ID(N'dbo.Supplier', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Supplier (
        supplierId varchar(20) NOT NULL CONSTRAINT PK_Supplier PRIMARY KEY,
        supplierName nvarchar(150) NOT NULL,
        address nvarchar(200) NULL,
        phoneNumber varchar(15) NULL,
        email varchar(100) NULL,
        logo varchar(255) NULL
    );
END;
GO

IF OBJECT_ID(N'dbo.Product', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Product (
        productId varchar(20) NOT NULL CONSTRAINT PK_Product PRIMARY KEY,
        productName nvarchar(150) NOT NULL,
        unitPrice decimal(12, 2) NULL,
        categoryId varchar(20) NULL,
        supplierId varchar(20) NULL,
        imgPath nvarchar(500) NULL,
        CONSTRAINT FK_Product_Category FOREIGN KEY (categoryId)
            REFERENCES dbo.Category(categoryId),
        CONSTRAINT FK_Product_Supplier FOREIGN KEY (supplierId)
            REFERENCES dbo.Supplier(supplierId)
    );
END;
GO

IF OBJECT_ID(N'dbo.ProductDetail', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.ProductDetail (
        productDetailId varchar(20) NOT NULL CONSTRAINT PK_ProductDetail PRIMARY KEY,
        [size] int NULL,
        color nvarchar(30) NULL,
        stockQuantity int NOT NULL CONSTRAINT DF_ProductDetail_stockQuantity DEFAULT (0),
        productId varchar(20) NULL,
        imgProductColor nvarchar(500) NULL,
        CONSTRAINT FK_ProductDetail_Product FOREIGN KEY (productId)
            REFERENCES dbo.Product(productId)
    );
END;
GO

IF OBJECT_ID(N'dbo.PurchaseOrder', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.PurchaseOrder (
        purchaseOrderId varchar(20) NOT NULL CONSTRAINT PK_PurchaseOrder PRIMARY KEY,
        orderDate datetime NOT NULL,
        employeeId varchar(20) NULL,
        supplierId varchar(20) NULL,
        [status] varchar(20) NOT NULL CONSTRAINT DF_PurchaseOrder_status DEFAULT ('Da nhap'),
        CONSTRAINT FK_PurchaseOrder_Employee FOREIGN KEY (employeeId)
            REFERENCES dbo.Employee(employeeId) ON DELETE SET NULL,
        CONSTRAINT FK_PurchaseOrder_Supplier FOREIGN KEY (supplierId)
            REFERENCES dbo.Supplier(supplierId)
    );
END;
GO

IF OBJECT_ID(N'dbo.PurchaseOrderDetail', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.PurchaseOrderDetail (
        purchaseOrderId varchar(20) NOT NULL,
        productDetailId varchar(20) NOT NULL,
        quantity int NOT NULL,
        importPrice decimal(12, 2) NOT NULL,
        CONSTRAINT PK_PurchaseOrderDetail PRIMARY KEY (purchaseOrderId, productDetailId),
        CONSTRAINT FK_PODetail_PurchaseOrder FOREIGN KEY (purchaseOrderId)
            REFERENCES dbo.PurchaseOrder(purchaseOrderId),
        CONSTRAINT FK_PODetail_ProductDetail FOREIGN KEY (productDetailId)
            REFERENCES dbo.ProductDetail(productDetailId)
    );
END;
GO

IF OBJECT_ID(N'dbo.Invoice', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Invoice (
        invoiceId varchar(20) NOT NULL CONSTRAINT PK_Invoice PRIMARY KEY,
        creationDate datetime NOT NULL,
        totalAmount decimal(12, 2) NOT NULL,
        employeeId varchar(20) NULL,
        customerId varchar(20) NULL,
        CONSTRAINT FK_Invoice_Employee FOREIGN KEY (employeeId)
            REFERENCES dbo.Employee(employeeId) ON DELETE SET NULL,
        CONSTRAINT FK_Invoice_Customer FOREIGN KEY (customerId)
            REFERENCES dbo.Customer(customerId)
    );
END;
GO

IF OBJECT_ID(N'dbo.InvoiceDetail', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.InvoiceDetail (
        invoiceId varchar(20) NOT NULL,
        productDetailId varchar(20) NOT NULL,
        quantity int NOT NULL,
        sellingPrice decimal(12, 2) NOT NULL,
        CONSTRAINT PK_InvoiceDetail PRIMARY KEY (invoiceId, productDetailId),
        CONSTRAINT FK_InvoiceDetail_Invoice FOREIGN KEY (invoiceId)
            REFERENCES dbo.Invoice(invoiceId),
        CONSTRAINT FK_InvoiceDetail_ProductDetail FOREIGN KEY (productDetailId)
            REFERENCES dbo.ProductDetail(productDetailId)
    );
END;
GO

IF OBJECT_ID(N'dbo.IDCounter', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.IDCounter (
        [prefix] varchar(10) NOT NULL CONSTRAINT PK_IDCounter PRIMARY KEY,
        currentValue int NOT NULL
    );
END;
GO

-- Add columns required by the current application when upgrading an older schema.
IF COL_LENGTH(N'dbo.Employee', N'email') IS NULL
    ALTER TABLE dbo.Employee ADD email varchar(100) NULL;
IF COL_LENGTH(N'dbo.Employee', N'password') IS NOT NULL
    ALTER TABLE dbo.Employee ALTER COLUMN [password] varchar(255) NULL;
ALTER TABLE dbo.Employee ALTER COLUMN [position] nvarchar(50) NOT NULL;
ALTER TABLE dbo.Account ALTER COLUMN [role] nvarchar(50) NOT NULL;

IF COL_LENGTH(N'dbo.Customer', N'loyaltyPoints') IS NULL
    ALTER TABLE dbo.Customer ADD loyaltyPoints int NOT NULL
        CONSTRAINT DF_Customer_loyaltyPoints_upgrade DEFAULT (0);
IF COL_LENGTH(N'dbo.Customer', N'imgCustomer') IS NULL
    ALTER TABLE dbo.Customer ADD imgCustomer nvarchar(500) NULL;

IF COL_LENGTH(N'dbo.PurchaseOrder', N'status') IS NULL
    ALTER TABLE dbo.PurchaseOrder ADD [status] varchar(20) NOT NULL
        CONSTRAINT DF_PurchaseOrder_status_upgrade DEFAULT ('Da nhap');

IF NOT EXISTS (
    SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_Invoice_Employee'
)
    ALTER TABLE dbo.Invoice WITH CHECK ADD CONSTRAINT FK_Invoice_Employee
        FOREIGN KEY (employeeId) REFERENCES dbo.Employee(employeeId) ON DELETE SET NULL;

IF NOT EXISTS (
    SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_PurchaseOrder_Employee'
)
    ALTER TABLE dbo.PurchaseOrder WITH CHECK ADD CONSTRAINT FK_PurchaseOrder_Employee
        FOREIGN KEY (employeeId) REFERENCES dbo.Employee(employeeId) ON DELETE SET NULL;
GO

INSERT dbo.IDCounter ([prefix], currentValue)
SELECT v.[prefix], 0
FROM (VALUES ('CAT'), ('HD'), ('HDD'), ('KH'), ('NCC'), ('NV'), ('PD'), ('PO'), ('SP'))
    AS v([prefix])
WHERE NOT EXISTS (
    SELECT 1 FROM dbo.IDCounter AS c WHERE c.[prefix] = v.[prefix]
);
GO

-- 1. EMPLOYEE
MERGE dbo.Employee AS target
USING (VALUES
    (N'NV002', N'Tran Thi Binh', 0, N'nv002@example.com', N'0901000002', N'Nhân viên bán hàng'),
    (N'NV003', N'Le Hoang Nam', 1, N'nv003@example.com', N'0901000003', N'Nhân viên bán hàng'),
    (N'NV004', N'Pham Thi Lan', 0, N'nv004@example.com', N'0901000004', N'Kế toán'),
    (N'NV005', N'Hoang Minh Duc', 1, N'nv005@example.com', N'0901000005', N'Nhân viên kho'),
    (N'NV006', N'Vo Thi Mai', 0, N'nv006@example.com', N'0901000006', N'Nhân viên bán hàng'),
    (N'NV007', N'Dang Quoc Huy', 1, N'nv007@example.com', N'0901000007', N'Nhân viên kho'),
    (N'NV008', N'Bui Ngoc Anh', 0, N'nv008@example.com', N'0901000008', N'Kế toán'),
    (N'NV009', N'Phan Thanh Tung', 1, N'nv009@example.com', N'0901000009', N'Nhân viên bán hàng'),
    (N'NV010', N'Do Thi Huong', 0, N'nv010@example.com', N'0901000010', N'Quản lý cửa hàng')
) AS source(employeeId, employeeName, gender, email, phoneNumber, position)
ON target.employeeId = source.employeeId
WHEN MATCHED THEN UPDATE SET
    employeeName = source.employeeName,
    gender = source.gender,
    email = source.email,
    phoneNumber = source.phoneNumber,
    position = source.position
WHEN NOT MATCHED BY TARGET THEN INSERT
    (employeeId, employeeName, gender, email, phoneNumber, position)
    VALUES (source.employeeId, source.employeeName, source.gender,
        source.email, source.phoneNumber, source.position);
GO

-- 2. ACCOUNT
MERGE dbo.Account AS target
USING (VALUES
    (N'NV002', N'123456', N'Employee'),
    (N'NV003', N'123456', N'Employee'),
    (N'NV004', N'123456', N'Employee'),
    (N'NV005', N'123456', N'Employee'),
    (N'NV006', N'123456', N'Employee'),
    (N'NV007', N'123456', N'Employee'),
    (N'NV008', N'123456', N'Employee'),
    (N'NV009', N'123456', N'Employee'),
    (N'NV010', N'123456', N'Admin')
) AS source(employeeId, [password], [role])
ON target.employeeId = source.employeeId
WHEN MATCHED THEN UPDATE SET
    [password] = source.[password],
    [role] = source.[role]
WHEN NOT MATCHED BY TARGET THEN INSERT
    (employeeId, [password], [role])
    VALUES (source.employeeId, source.[password], source.[role]);
GO

-- 3. CUSTOMER
MERGE dbo.Customer AS target
USING (VALUES
    (N'KH001', N'Nguyen Minh Khang', N'Quan 1, TP.HCM', N'0912000001', 120),
    (N'KH002', N'Tran Ngoc Ha', N'Quan 3, TP.HCM', N'0912000002', 50),
    (N'KH003', N'Le Quoc Bao', N'Go Vap, TP.HCM', N'0912000003', 200),
    (N'KH004', N'Pham Thanh Truc', N'Tan Binh, TP.HCM', N'0912000004', 80),
    (N'KH005', N'Hoang Gia Huy', N'Thu Duc, TP.HCM', N'0912000005', 300),
    (N'KH006', N'Vo Thi Ngoc', N'Binh Thanh, TP.HCM', N'0912000006', 40),
    (N'KH007', N'Dang Minh Quan', N'Quan 7, TP.HCM', N'0912000007', 150),
    (N'KH008', N'Bui Khanh Linh', N'Quan 5, TP.HCM', N'0912000008', 90),
    (N'KH009', N'Phan Tuan Kiet', N'Quan 10, TP.HCM', N'0912000009', 60),
    (N'KH010', N'Do My Duyen', N'Binh Tan, TP.HCM', N'0912000010', 180)
) AS source(customerId, customerName, address, phoneNumber, loyaltyPoints)
ON target.customerId = source.customerId
WHEN MATCHED THEN UPDATE SET
    customerName = source.customerName,
    address = source.address,
    phoneNumber = source.phoneNumber,
    loyaltyPoints = source.loyaltyPoints
WHEN NOT MATCHED BY TARGET THEN INSERT
    (customerId, customerName, address, phoneNumber, loyaltyPoints)
    VALUES (source.customerId, source.customerName, source.address,
        source.phoneNumber, source.loyaltyPoints);
GO

-- 4. SUPPLIER
MERGE dbo.Supplier AS target
USING (VALUES
    (N'NCC001', N'Adidas Viet Nam', N'Quan 1, TP.HCM', N'02830000001', N'adidas@gmail.com', N'AdidasLogo.png'),
    (N'NCC002', N'Beyono Shoes', N'Quan 3, TP.HCM', N'02830000002', N'beyono@gmail.com', N'BeyonoLogo.png'),
    (N'NCC003', N'Cabeto Footwear', N'Quan 5, TP.HCM', N'02830000003', N'cabeto@gmail.com', N'CabetoLogo.png'),
    (N'NCC004', N'Designer Shoes', N'Quan 7, TP.HCM', N'02830000004', N'designer@gmail.com', N'Designer ShoesLogo.png'),
    (N'NCC005', N'Urban Shoes', N'Binh Thanh, TP.HCM', N'02830000005', N'urban@gmail.com', N'Urban ShoesLogo.png'),
    (N'NCC006', N'Sporty Vietnam', N'Go Vap, TP.HCM', N'02830000006', N'sporty@gmail.com', N'Sporty VietnamLogo.png'),
    (N'NCC007', N'Fashion Footwear', N'Tan Binh, TP.HCM', N'02830000007', N'fashion@gmail.com', N'Fashion FootwearLogo.png'),
    (N'NCC008', N'Classic Shoes', N'Thu Duc, TP.HCM', N'02830000008', N'classic@gmail.com', N'Classic ShoesLogo.png'),
    (N'NCC009', N'Premium Shoes', N'Quan 10, TP.HCM', N'02830000009', N'premium@gmail.com', N'Premium ShoesLogo.png'),
    (N'NCC010', N'Street Style', N'Quan 12, TP.HCM', N'02830000010', N'street@gmail.com', N'Street StyleLogo.png')
) AS source(supplierId, supplierName, address, phoneNumber, email, logo)
ON target.supplierId = source.supplierId
WHEN MATCHED THEN UPDATE SET
    supplierName = source.supplierName,
    address = source.address,
    phoneNumber = source.phoneNumber,
    email = source.email,
    logo = source.logo
WHEN NOT MATCHED BY TARGET THEN INSERT
    (supplierId, supplierName, address, phoneNumber, email, logo)
    VALUES (source.supplierId, source.supplierName, source.address,
        source.phoneNumber, source.email, source.logo);
GO

-- 5. CATEGORY
MERGE dbo.Category AS target
USING (VALUES
    (N'CAT001', N'Giày thể thao'),
    (N'CAT002', N'Giày chạy bộ'),
    (N'CAT003', N'Giày bóng đá'),
    (N'CAT004', N'Giày sneaker'),
    (N'CAT005', N'Giày thời trang'),
    (N'CAT006', N'Giày bóng rổ'),
    (N'CAT007', N'Giày cầu lông'),
    (N'CAT008', N'Giày lười'),
    (N'CAT009', N'Giày da'),
    (N'CAT010', N'Giày casual')
) AS source(categoryId, categoryName)
ON target.categoryId = source.categoryId
WHEN MATCHED THEN UPDATE SET categoryName = source.categoryName
WHEN NOT MATCHED BY TARGET THEN INSERT (categoryId, categoryName)
    VALUES (source.categoryId, source.categoryName);
GO

-- 6. PRODUCT
MERGE dbo.Product AS target
USING (VALUES
    (N'SP001', N'Adidas Ultraboost', 2500000.00, N'CAT002', N'NCC001'),
    (N'SP002', N'Adidas Superstar', 1800000.00, N'CAT004', N'NCC001'),
    (N'SP003', N'Beyono Runner', 950000.00, N'CAT002', N'NCC002'),
    (N'SP004', N'Cabeto Classic', 1200000.00, N'CAT010', N'NCC003'),
    (N'SP005', N'Designer Street', 1500000.00, N'CAT005', N'NCC004'),
    (N'SP006', N'Urban Sport', 1100000.00, N'CAT001', N'NCC005'),
    (N'SP007', N'Sporty Football', 1350000.00, N'CAT003', N'NCC006'),
    (N'SP008', N'Fashion Sneaker', 1650000.00, N'CAT004', N'NCC007'),
    (N'SP009', N'Classic Leather', 2200000.00, N'CAT009', N'NCC008'),
    (N'SP010', N'Street Casual', 1250000.00, N'CAT010', N'NCC010')
) AS source(productId, productName, unitPrice, categoryId, supplierId)
ON target.productId = source.productId
WHEN MATCHED THEN UPDATE SET
    productName = source.productName,
    unitPrice = source.unitPrice,
    categoryId = source.categoryId,
    supplierId = source.supplierId
WHEN NOT MATCHED BY TARGET THEN INSERT
    (productId, productName, unitPrice, categoryId, supplierId)
    VALUES (source.productId, source.productName, source.unitPrice,
        source.categoryId, source.supplierId);
GO

-- 7. PRODUCT DETAIL
MERGE dbo.ProductDetail AS target
USING (VALUES
    (N'PD001', 42, N'Black', 20, N'SP001', N'adidas.png'),
    (N'PD002', 41, N'White', 15, N'SP002', N'adidas.png'),
    (N'PD003', 40, N'Blue', 25, N'SP003', N'beyono.png'),
    (N'PD004', 42, N'Brown', 18, N'SP004', N'cabeto.jpg'),
    (N'PD005', 39, N'Black', 12, N'SP005', N'designer.png'),
    (N'PD006', 41, N'White', 30, N'SP006', N'beyono.png'),
    (N'PD007', 42, N'Red', 16, N'SP007', N'cabeto.jpg'),
    (N'PD008', 40, N'Beige', 22, N'SP008', N'designer.png'),
    (N'PD009', 43, N'Brown', 10, N'SP009', N'cabeto.jpg'),
    (N'PD010', 41, N'Gray', 28, N'SP010', N'beyono.png')
) AS source(productDetailId, [size], color, stockQuantity, productId, imgProductColor)
ON target.productDetailId = source.productDetailId
WHEN MATCHED THEN UPDATE SET
    [size] = source.[size],
    color = source.color,
    stockQuantity = source.stockQuantity,
    productId = source.productId,
    imgProductColor = source.imgProductColor
WHEN NOT MATCHED BY TARGET THEN INSERT
    (productDetailId, [size], color, stockQuantity, productId, imgProductColor)
    VALUES (source.productDetailId, source.[size], source.color,
        source.stockQuantity, source.productId, source.imgProductColor);
GO

-- 8. PURCHASE ORDER
MERGE dbo.PurchaseOrder AS target
USING (VALUES
    (N'PO001', CONVERT(datetime, '20260901', 112), N'NV005', N'NCC001', N'Da nhap'),
    (N'PO002', CONVERT(datetime, '20260902', 112), N'NV007', N'NCC001', N'Da nhap'),
    (N'PO003', CONVERT(datetime, '20260903', 112), N'NV005', N'NCC002', N'Da nhap'),
    (N'PO004', CONVERT(datetime, '20260904', 112), N'NV007', N'NCC003', N'Da nhap'),
    (N'PO005', CONVERT(datetime, '20260905', 112), N'NV005', N'NCC004', N'Da nhap'),
    (N'PO006', CONVERT(datetime, '20260906', 112), N'NV007', N'NCC005', N'Da nhap'),
    (N'PO007', CONVERT(datetime, '20260907', 112), N'NV005', N'NCC006', N'Da nhap'),
    (N'PO008', CONVERT(datetime, '20260908', 112), N'NV007', N'NCC007', N'Da nhap'),
    (N'PO009', CONVERT(datetime, '20260909', 112), N'NV005', N'NCC008', N'Da nhap'),
    (N'PO010', CONVERT(datetime, '20260910', 112), N'NV007', N'NCC010', N'Da nhap')
) AS source(purchaseOrderId, orderDate, employeeId, supplierId, [status])
ON target.purchaseOrderId = source.purchaseOrderId
WHEN MATCHED THEN UPDATE SET
    orderDate = source.orderDate,
    employeeId = source.employeeId,
    supplierId = source.supplierId,
    [status] = source.[status]
WHEN NOT MATCHED BY TARGET THEN INSERT
    (purchaseOrderId, orderDate, employeeId, supplierId, [status])
    VALUES (source.purchaseOrderId, source.orderDate, source.employeeId,
        source.supplierId, source.[status]);
GO

-- 9. PURCHASE ORDER DETAIL
MERGE dbo.PurchaseOrderDetail AS target
USING (VALUES
    (N'PO001', N'PD001', 20, 1800000.00),
    (N'PO002', N'PD002', 15, 1250000.00),
    (N'PO003', N'PD003', 25, 650000.00),
    (N'PO004', N'PD004', 18, 800000.00),
    (N'PO005', N'PD005', 12, 1000000.00),
    (N'PO006', N'PD006', 30, 750000.00),
    (N'PO007', N'PD007', 16, 900000.00),
    (N'PO008', N'PD008', 22, 1100000.00),
    (N'PO009', N'PD009', 10, 1500000.00),
    (N'PO010', N'PD010', 28, 800000.00)
) AS source(purchaseOrderId, productDetailId, quantity, importPrice)
ON target.purchaseOrderId = source.purchaseOrderId
    AND target.productDetailId = source.productDetailId
WHEN MATCHED THEN UPDATE SET
    quantity = source.quantity,
    importPrice = source.importPrice
WHEN NOT MATCHED BY TARGET THEN INSERT
    (purchaseOrderId, productDetailId, quantity, importPrice)
    VALUES (source.purchaseOrderId, source.productDetailId,
        source.quantity, source.importPrice);
GO

-- 10. INVOICE
MERGE dbo.Invoice AS target
USING (VALUES
    (N'HD001', CONVERT(datetime, '20261001', 112), 2500000.00, N'NV002', N'KH001'),
    (N'HD002', CONVERT(datetime, '20261002', 112), 1800000.00, N'NV003', N'KH002'),
    (N'HD003', CONVERT(datetime, '20261003', 112), 950000.00, N'NV004', N'KH003'),
    (N'HD004', CONVERT(datetime, '20261004', 112), 2400000.00, N'NV006', N'KH004'),
    (N'HD005', CONVERT(datetime, '20261005', 112), 1500000.00, N'NV008', N'KH005'),
    (N'HD006', CONVERT(datetime, '20261006', 112), 1100000.00, N'NV009', N'KH006'),
    (N'HD007', CONVERT(datetime, '20261007', 112), 2700000.00, N'NV002', N'KH007'),
    (N'HD008', CONVERT(datetime, '20261008', 112), 1650000.00, N'NV003', N'KH008'),
    (N'HD009', CONVERT(datetime, '20261009', 112), 2200000.00, N'NV004', N'KH009'),
    (N'HD010', CONVERT(datetime, '20261010', 112), 2500000.00, N'NV006', N'KH010')
) AS source(invoiceId, creationDate, totalAmount, employeeId, customerId)
ON target.invoiceId = source.invoiceId
WHEN MATCHED THEN UPDATE SET
    creationDate = source.creationDate,
    totalAmount = source.totalAmount,
    employeeId = source.employeeId,
    customerId = source.customerId
WHEN NOT MATCHED BY TARGET THEN INSERT
    (invoiceId, creationDate, totalAmount, employeeId, customerId)
    VALUES (source.invoiceId, source.creationDate, source.totalAmount,
        source.employeeId, source.customerId);
GO

-- 11. INVOICE DETAIL
MERGE dbo.InvoiceDetail AS target
USING (VALUES
    (N'HD001', N'PD001', 1, 2500000.00),
    (N'HD002', N'PD002', 1, 1800000.00),
    (N'HD003', N'PD003', 1, 950000.00),
    (N'HD004', N'PD004', 2, 1200000.00),
    (N'HD005', N'PD005', 1, 1500000.00),
    (N'HD006', N'PD006', 1, 1100000.00),
    (N'HD007', N'PD007', 2, 1350000.00),
    (N'HD008', N'PD008', 1, 1650000.00),
    (N'HD009', N'PD009', 1, 2200000.00),
    (N'HD010', N'PD001', 1, 2500000.00)
) AS source(invoiceId, productDetailId, quantity, sellingPrice)
ON target.invoiceId = source.invoiceId
    AND target.productDetailId = source.productDetailId
WHEN MATCHED THEN UPDATE SET
    quantity = source.quantity,
    sellingPrice = source.sellingPrice
WHEN NOT MATCHED BY TARGET THEN INSERT
    (invoiceId, productDetailId, quantity, sellingPrice)
    VALUES (source.invoiceId, source.productDetailId,
        source.quantity, source.sellingPrice);
GO

UPDATE c
SET currentValue = CASE WHEN c.currentValue < 10 THEN 10 ELSE c.currentValue END
FROM dbo.IDCounter AS c
WHERE c.[prefix] IN ('NV', 'KH', 'NCC', 'CAT', 'SP', 'PD', 'PO', 'HD');
GO
