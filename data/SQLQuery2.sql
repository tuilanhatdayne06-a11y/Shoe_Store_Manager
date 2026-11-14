-- 1. EMPLOYEE
USE ShoeStore
go
    INSERT INTO Employee
    (employeeId, employeeName, gender, phoneNumber, position)
    VALUES
    ('NV002', 'Tran Thi Binh', 0, '0901000002', 'Nhan vien ban hang'),
    ('NV003', 'Le Hoang Nam', 1, '0901000003', 'Nhan vien ban hang'),
    ('NV004', 'Pham Thi Lan', 0, '0901000004', 'Thu ngan'),
    ('NV005', 'Hoang Minh Duc', 1, '0901000005', 'Nhan vien kho'),
    ('NV006', 'Vo Thi Mai', 0, '0901000006', 'Nhan vien ban hang'),
    ('NV007', 'Dang Quoc Huy', 1, '0901000007', 'Nhan vien kho'),
    ('NV008', 'Bui Ngoc Anh', 0, '0901000008', 'Thu ngan'),
    ('NV009', 'Phan Thanh Tung', 1, '0901000009', 'Nhan vien ban hang'),
    ('NV010', 'Do Thi Huong', 0, '0901000010', 'Quan ly');
    UPDATE Employee SET email = 'nhat.truong@gmail.com' WHERE employeeId = 'NV001';
    UPDATE Employee SET email = 'an.nguyen@gmail.com' WHERE employeeId = 'NV002';
    UPDATE Employee SET email = 'bich.tran@gmail.com' WHERE employeeId = 'NV003';
    UPDATE Employee SET email = 'yen.le@gmail.com' WHERE employeeId = 'NV004';
    UPDATE Employee SET email = 'tuan.pham@gmail.com' WHERE employeeId = 'NV005';
    -- 2. ACCOUNT
    INSERT INTO Account
    (employeeId, password, role)
    VALUES

    ('NV002', '123456', 'Employee'),
    ('NV003', '123456', 'Employee'),
    ('NV004', '123456', 'Employee'),
    ('NV005', '123456', 'Employee'),
    ('NV006', '123456', 'Employee'),
    ('NV007', '123456', 'Employee'),
    ('NV008', '123456', 'Employee'),
    ('NV009', '123456', 'Employee'),
    ('NV010', '123456', 'Admin');

    -- 3. CUSTOMER
    INSERT INTO Customer
    (customerId, customerName, address, phoneNumber, loyaltyPoints)
    VALUES
    ('KH001', 'Nguyen Minh Khang', 'Quan 1, TP.HCM', '0912000001', 120),
    ('KH002', 'Tran Ngoc Ha', 'Quan 3, TP.HCM', '0912000002', 50),
    ('KH003', 'Le Quoc Bao', 'Go Vap, TP.HCM', '0912000003', 200),
    ('KH004', 'Pham Thanh Truc', 'Tan Binh, TP.HCM', '0912000004', 80),
    ('KH005', 'Hoang Gia Huy', 'Thu Duc, TP.HCM', '0912000005', 300),
    ('KH006', 'Vo Thi Ngoc', 'Binh Thanh, TP.HCM', '0912000006', 40),
    ('KH007', 'Dang Minh Quan', 'Quan 7, TP.HCM', '0912000007', 150),
    ('KH008', 'Bui Khanh Linh', 'Quan 5, TP.HCM', '0912000008', 90),
    ('KH009', 'Phan Tuan Kiet', 'Quan 10, TP.HCM', '0912000009', 60),
    ('KH010', 'Do My Duyen', 'Binh Tan, TP.HCM', '0912000010', 180);

    -- 4. SUPPLIER
    INSERT INTO Supplier
    (supplierId, supplierName, address, phoneNumber, email, logo)
    VALUES
    ('NCC001', 'Adidas Viet Nam', 'Quan 1, TP.HCM', '02830000001', 'adidas@gmail.com', 'adidas.png'),
    ('NCC002', 'Beyono Shoes', 'Quan 3, TP.HCM', '02830000002', 'beyono@gmail.com', 'beyono.png'),
    ('NCC003', 'Cabeto Footwear', 'Quan 5, TP.HCM', '02830000003', 'cabeto@gmail.com', 'cabeto.jpg'),
    ('NCC004', 'Designer Shoes', 'Quan 7, TP.HCM', '02830000004', 'designer@gmail.com', 'designer.png'),
    ('NCC005', 'Urban Shoes', 'Binh Thanh, TP.HCM', '02830000005', 'urban@gmail.com', 'Logo.png'),
    ('NCC006', 'Sporty Vietnam', 'Go Vap, TP.HCM', '02830000006', 'sporty@gmail.com', NULL),
    ('NCC007', 'Fashion Footwear', 'Tan Binh, TP.HCM', '02830000007', 'fashion@gmail.com', NULL),
    ('NCC008', 'Classic Shoes', 'Thu Duc, TP.HCM', '02830000008', 'classic@gmail.com', NULL),
    ('NCC009', 'Premium Shoes', 'Quan 10, TP.HCM', '02830000009', 'premium@gmail.com', NULL),
    ('NCC010', 'Street Style', 'Quan 12, TP.HCM', '02830000010', 'street@gmail.com', NULL);

    -- 5. CATEGORY
    INSERT INTO Category
    (categoryId, categoryName)
    VALUES
    ('CAT001', 'Giay the thao'),
    ('CAT002', 'Giay chay bo'),
    ('CAT003', 'Giay bong da'),
    ('CAT004', 'Giay sneaker'),
    ('CAT005', 'Giay thoi trang'),
    ('CAT006', 'Giay bong ro'),
    ('CAT007', 'Giay cau long'),
    ('CAT008', 'Giay luoi'),
    ('CAT009', 'Giay da'),
    ('CAT010', 'Giay casual');

    -- 6. PRODUCT
    INSERT INTO Product
    (productId, productName, unitPrice, categoryId, supplierId)
    VALUES
    ('SP001', 'Adidas Ultraboost', 2500000, 'CAT002', 'NCC001'),
    ('SP002', 'Adidas Superstar', 1800000, 'CAT004', 'NCC001'),
    ('SP003', 'Beyono Runner', 950000, 'CAT002', 'NCC002'),
    ('SP004', 'Cabeto Classic', 1200000, 'CAT010', 'NCC003'),
    ('SP005', 'Designer Street', 1500000, 'CAT005', 'NCC004'),
    ('SP006', 'Urban Sport', 1100000, 'CAT001', 'NCC005'),
    ('SP007', 'Sporty Football', 1350000, 'CAT003', 'NCC006'),
    ('SP008', 'Fashion Sneaker', 1650000, 'CAT004', 'NCC007'),
    ('SP009', 'Classic Leather', 2200000, 'CAT009', 'NCC008'),
    ('SP010', 'Street Casual', 1250000, 'CAT010', 'NCC010');

    -- 7. PRODUCTDETAIL
    INSERT INTO ProductDetail
    (productDetailId, size, color, stockQuantity, productId, imgProductColor)
    VALUES
    ('PD001', 42, 'Black', 20, 'SP001', 'adidas.png'),
    ('PD002', 41, 'White', 15, 'SP002', 'adidas.png'),
    ('PD003', 40, 'Blue', 25, 'SP003', 'beyono.png'),
    ('PD004', 42, 'Brown', 18, 'SP004', 'cabeto.jpg'),
    ('PD005', 39, 'Black', 12, 'SP005', 'designer.png'),
    ('PD006', 41, 'White', 30, 'SP006', 'beyono.png'),
    ('PD007', 42, 'Red', 16, 'SP007', 'cabeto.jpg'),
    ('PD008', 40, 'Beige', 22, 'SP008', 'designer.png'),
    ('PD009', 43, 'Brown', 10, 'SP009', 'cabeto.jpg'),
    ('PD010', 41, 'Gray', 28, 'SP010', 'beyono.png');

    -- 8. PURCHASEORDER
    INSERT INTO PurchaseOrder
    (purchaseOrderId, orderDate, employeeId, supplierId, status)
    VALUES
    ('PO001', '2026-09-01', 'NV005', 'NCC001', 'Da nhap'),
    ('PO002', '2026-09-02', 'NV007', 'NCC001', 'Da nhap'),
    ('PO003', '2026-09-03', 'NV005', 'NCC002', 'Da nhap'),
    ('PO004', '2026-09-04', 'NV007', 'NCC003', 'Da nhap'),
    ('PO005', '2026-09-05', 'NV005', 'NCC004', 'Da nhap'),
    ('PO006', '2026-09-06', 'NV007', 'NCC005', 'Da nhap'),
    ('PO007', '2026-09-07', 'NV005', 'NCC006', 'Da nhap'),
    ('PO008', '2026-09-08', 'NV007', 'NCC007', 'Da nhap'),
    ('PO009', '2026-09-09', 'NV005', 'NCC008', 'Da nhap'),
    ('PO010', '2026-09-10', 'NV007', 'NCC010', 'Da nhap');

    -- 9. PURCHASEORDERDETAIL
    INSERT INTO PurchaseOrderDetail
    (purchaseOrderId, productDetailId, quantity, importPrice)
    VALUES
    ('PO001', 'PD001', 20, 1800000),
    ('PO002', 'PD002', 15, 1250000),
    ('PO003', 'PD003', 25, 650000),
    ('PO004', 'PD004', 18, 800000),
    ('PO005', 'PD005', 12, 1000000),
    ('PO006', 'PD006', 30, 750000),
    ('PO007', 'PD007', 16, 900000),
    ('PO008', 'PD008', 22, 1100000),
    ('PO009', 'PD009', 10, 1500000),
    ('PO010', 'PD010', 28, 800000);

    -- 10. INVOICE
    INSERT INTO Invoice
    (invoiceId, creationDate, totalAmount, employeeId, customerId)
    VALUES
    ('HD001', '2026-10-01', 2500000, 'NV002', 'KH001'),
    ('HD002', '2026-10-02', 1800000, 'NV003', 'KH002'),
    ('HD003', '2026-10-03', 950000, 'NV004', 'KH003'),
    ('HD004', '2026-10-04', 2400000, 'NV006', 'KH004'),
    ('HD005', '2026-10-05', 1500000, 'NV008', 'KH005'),
    ('HD006', '2026-10-06', 1100000, 'NV009', 'KH006'),
    ('HD007', '2026-10-07', 2700000, 'NV002', 'KH007'),
    ('HD008', '2026-10-08', 1650000, 'NV003', 'KH008'),
    ('HD009', '2026-10-09', 2200000, 'NV004', 'KH009'),
    ('HD010', '2026-10-10', 2500000, 'NV006', 'KH010');

    -- 11. INVOICEDETAIL
    INSERT INTO InvoiceDetail
    (invoiceId, productDetailId, quantity, sellingPrice)
    VALUES
    ('HD001', 'PD001', 1, 2500000),
    ('HD002', 'PD002', 1, 1800000),
    ('HD003', 'PD003', 1, 950000),
    ('HD004', 'PD004', 2, 1200000),
    ('HD005', 'PD005', 1, 1500000),
    ('HD006', 'PD006', 1, 1100000),
    ('HD007', 'PD007', 2, 1350000),
    ('HD008', 'PD008', 1, 1650000),
    ('HD009', 'PD009', 1, 2200000),
    ('HD010', 'PD001', 1, 2500000);

    INSERT INTO IDCounter (prefix, currentValue)
VALUES
('NV', 10),
('KH', 10),
('NCC', 10),
('CAT', 10),
('SP', 10),
('PD', 10),
('PO', 10),
('HD', 10);