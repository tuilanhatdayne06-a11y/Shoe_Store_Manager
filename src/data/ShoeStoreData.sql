USE ShoeStore;
GO

-- Du lieu mau cho database ShoeStore
-- Moi bang nghiep vu co 15 dong du lieu.
-- IDCounter duoc khoi tao voi currentValue = 15.

DELETE FROM InvoiceDetail;
DELETE FROM Invoice;

DELETE FROM PurchaseOrderDetail;
DELETE FROM PurchaseOrder;

DELETE FROM ProductDetail;
DELETE FROM Product;

DELETE FROM Account;
DELETE FROM Employee;

DELETE FROM Customer;
DELETE FROM Supplier;
DELETE FROM Category;

DELETE FROM IDCounter;

-- 1. Employee
INSERT INTO Employee (employeeId, employeeName, gender, phoneNumber, position, email) VALUES
('NV001', N'Nguyen Van An', 1, '0901000001', N'Quan ly', 'an.nguyen@shoestore.vn'),
('NV002', N'Tran Thi Binh', 0, '0901000002', N'Nhan vien ban hang', 'binh.tran@shoestore.vn'),
('NV003', N'Le Van Cuong', 1, '0901000003', N'Nhan vien ban hang', 'cuong.le@shoestore.vn'),
('NV004', N'Pham Thi Dung', 0, '0901000004', N'Nhan vien ban hang', 'dung.pham@shoestore.vn'),
('NV005', N'Hoang Van Giang', 1, '0901000005', N'Nhan vien kho', 'giang.hoang@shoestore.vn'),
('NV006', N'Vu Thi Ha', 0, '0901000006', N'Nhan vien ban hang', 'ha.vu@shoestore.vn'),
('NV007', N'Dang Van Hung', 1, '0901000007', N'Nhan vien ban hang', 'hung.dang@shoestore.vn'),
('NV008', N'Bui Thi Lan', 0, '0901000008', N'Nhan vien kho', 'lan.bui@shoestore.vn'),
('NV009', N'Do Van Minh', 1, '0901000009', N'Nhan vien kho', 'minh.do@shoestore.vn'),
('NV010', N'Ngo Thi Nga', 0, '0901000010', N'Nhan vien ban hang', 'nga.ngo@shoestore.vn'),
('NV011', N'Duong Van Phuc', 1, '0901000011', N'Nhan vien ban hang', 'phuc.duong@shoestore.vn'),
('NV012', N'Ly Thi Quyen', 0, '0901000012', N'Quan ly', 'quyen.ly@shoestore.vn'),
('NV013', N'Ta Van Son', 1, '0901000013', N'Nhan vien kho', 'son.ta@shoestore.vn'),
('NV014', N'Phan Thi Thao', 0, '0901000014', N'Nhan vien ban hang', 'thao.phan@shoestore.vn'),
('NV015', N'Mai Van Tuan', 1, '0901000015', N'Nhan vien kho', 'tuan.mai@shoestore.vn');
GO

-- 2. Account
INSERT INTO Account (employeeId, password, role) VALUES
('NV001', '123456', N'Admin'),
('NV002', '123456', N'Staff'),
('NV003', '123456', N'Staff'),
('NV004', '123456', N'Staff'),
('NV005', '123456', N'Staff'),
('NV006', '123456', N'Staff'),
('NV007', '123456', N'Staff'),
('NV008', '123456', N'Staff'),
('NV009', '123456', N'Staff'),
('NV010', '123456', N'Staff'),
('NV011', '123456', N'Staff'),
('NV012', '123456', N'Admin'),
('NV013', '123456', N'Staff'),
('NV014', '123456', N'Staff'),
('NV015', '123456', N'Staff');
GO

-- 3. Customer
INSERT INTO Customer (customerId, customerName, address, phoneNumber, loyaltyPoints, gender) VALUES
('KH001', N'Nguyen Minh Chau', N'Quan 1, TP Ho Chi Minh', '0912000001', 12, 0),
('KH002', N'Tran Quoc Bao', N'Quan 3, TP Ho Chi Minh', '0912000002', 8, 1),
('KH003', N'Le Thanh Tam', N'Go Vap, TP Ho Chi Minh', '0912000003', 20, 1),
('KH004', N'Pham Ngoc Anh', N'Thu Duc, TP Ho Chi Minh', '0912000004', 15, 0),
('KH005', N'Hoang Gia Huy', N'Bien Hoa, Dong Nai', '0912000005', 5, 1),
('KH006', N'Vu Khanh Linh', N'Quan 7, TP Ho Chi Minh', '0912000006', 18, 0),
('KH007', N'Dang Tuan Kiet', N'Quan 10, TP Ho Chi Minh', '0912000007', 9, 1),
('KH008', N'Bui Thuy Duong', N'Binh Thanh, TP Ho Chi Minh', '0912000008', 25, 0),
('KH009', N'Do Hoang Nam', N'Tan Binh, TP Ho Chi Minh', '0912000009', 11, 1),
('KH010', N'Ngo Phuong Thao', N'Quan 5, TP Ho Chi Minh', '0912000010', 7, 0),
('KH011', N'Duong Anh Khoa', N'Di An, Binh Duong', '0912000011', 14, 1),
('KH012', N'Ly Bao Tram', N'Quan 4, TP Ho Chi Minh', '0912000012', 30, 0),
('KH013', N'Ta Duc Thinh', N'Quan 12, TP Ho Chi Minh', '0912000013', 6, 1),
('KH014', N'Phan Nhu Quynh', N'Phu Nhuan, TP Ho Chi Minh', '0912000014', 22, 0),
('KH015', N'Mai Thanh Tung', N'Thu Dau Mot, Binh Duong', '0912000015', 10, 1);
GO

-- 4. Supplier
-- Supplier la cac don vi phan phoi, khong phai ten thuong hieu giay.
-- Khong gan anh logo vi danh sach anh khong co logo nha phan phoi phu hop.
INSERT INTO Supplier (supplierId, supplierName, address, phoneNumber, email, logo) VALUES
('NCC001', N'Cong ty Thuong mai Minh Phat', N'Quan 5, TP Ho Chi Minh', '02838000002', 'contact02@minhphat.vn', 'BeyonoLogo.png'),
('NCC002', N'Cong ty Phan phoi An Khang', N'Binh Thanh, TP Ho Chi Minh', '02838000003', 'contact03@ankhang.vn', 'CabetoLogo.png'),
('NCC003', N'Cong ty Thuong mai Hoang Gia', N'Thu Duc, TP Ho Chi Minh', '02838000004', 'contact04@hoanggia.vn', 'Classic ShoesLogo.png'),
('NCC004', N'Cong ty Phan phoi Thanh Cong', N'Quan 10, TP Ho Chi Minh', '02838000005', 'contact05@thanhcong.vn', 'Designer ShoesLogo.png'),
('NCC005', N'Cong ty Thuong mai Dai Tin', N'Quan 3, TP Ho Chi Minh', '02838000006', 'contact06@daitin.vn', 'Fashion FootwearLogo.png'),
('NCC006', N'Cong ty Phan phoi Nam Viet', N'Go Vap, TP Ho Chi Minh', '02838000007', 'contact07@namviet.vn', 'Premium ShoesLogo.png'),
('NCC007', N'Cong ty Thuong mai Phu Hung', N'Tan Binh, TP Ho Chi Minh', '02838000008', 'contact08@phuhung.vn', 'Sporty VietnamLogo.png'),
('NCC008', N'Cong ty Phan phoi Tien Dat', N'Quan 7, TP Ho Chi Minh', '02838000009', 'contact09@tiendat.vn', 'Street StyleLogo.png'),
('NCC009', N'Cong ty Thuong mai Bao Tin', N'Quan 11, TP Ho Chi Minh', '02838000010', 'contact10@baotin.vn', 'Urban ShoesLogo.png');
GO

-- 5. Category
INSERT INTO Category (categoryId, categoryName) VALUES
('CAT001', N'Giay the thao'),
('CAT002', N'Giay chay bo'),
('CAT003', N'Giay bong ro'),
('CAT004', N'Giay da bong'),
('CAT005', N'Giay sneaker'),
('CAT006', N'Giay casual'),
('CAT007', N'Giay cong so'),
('CAT008', N'Giay luoi'),
('CAT009', N'Giay boot'),
('CAT010', N'Giay sandal'),
('CAT011', N'Dep the thao'),
('CAT012', N'Giay tap gym'),
('CAT013', N'Giay trekking'),
('CAT014', N'Giay tennis'),
('CAT015', N'Giay lifestyle');
GO

-- 6. Product
INSERT INTO Product (productId, productName, unitPrice, categoryId, supplierId) VALUES
('SP001', N'Giay chay bo nang nhe', 1250000, 'CAT002', 'NCC001'),
('SP002', N'Giay bong ro co cao', 1850000, 'CAT003', 'NCC002'),
('SP003', N'Giay sneaker co ban', 990000, 'CAT005', 'NCC003'),
('SP004', N'Giay the thao da nang', 1150000, 'CAT001', 'NCC004'),
('SP005', N'Giay da bong san co', 1450000, 'CAT004', 'NCC005'),
('SP006', N'Giay casual vai', 750000, 'CAT006', 'NCC006'),
('SP007', N'Giay cong so nam', 1350000, 'CAT007', 'NCC007'),
('SP008', N'Giay luoi thoang khi', 680000, 'CAT008', 'NCC008'),
('SP009', N'Boot co ngan', 1650000, 'CAT009', 'NCC009'),
('SP010', N'Sandal quai ngang', 520000, 'CAT010', 'NCC001'),
('SP011', N'Dep the thao mem', 350000, 'CAT011', 'NCC002'),
('SP012', N'Giay tap gym on dinh', 1050000, 'CAT012', 'NCC003'),
('SP013', N'Giay trekking ben', 1950000, 'CAT013', 'NCC004'),
('SP014', N'Giay tennis linh hoat', 1550000, 'CAT014', 'NCC005'),
('SP015', N'Giay lifestyle hien dai', 1280000, 'CAT015', 'NCC006'),
('SP016', 'Adidas Predator Elite Fold-over Tongue FG', 4500000, 'CAT003', 'NCC001'),
('SP017', 'Asics Sky Elite FF MT 3 White Dragon Fruit', 3200000, 'CAT001', 'NCC002'),
('SP018', 'Yonex Power Cushion Infinity 2', 3800000, 'CAT001', 'NCC003'),
('SP019', 'LOW BASKET G BEG/SAX', 1800000, 'CAT010', 'NCC004'),
('SP020', 'Ja 4 Deep Water EP', 3500000, 'CAT001', 'NCC005'),
('SP021', 'Adizero Adios Pro EVO 1', 6500000, 'CAT002', 'NCC001');

GO

-- 7. ProductDetail
-- Cac ten anh duoi day deu nam trong danh sach anh duoc cung cap.
-- Khong su dung file co ten chua tu "logo".
INSERT INTO ProductDetail (productDetailId, size, color, stockQuantity, productId, imgProductColor) VALUES
('PD001', 40, N'Den', 18, 'SP001', N'running.png'),
('PD002', 41, N'Trang', 12, 'SP002', N'basketball.png'),
('PD003', 39, N'Trang den', 20, 'SP003', N'ultraboost.jpg'),
('PD004', 42, N'Xam', 15, 'SP004', N'moon.png'),
('PD005', 41, N'Den do', 14, 'SP005', N'soccer.png'),
('PD006', 38, N'Be', 22, 'SP006', N'ultraboost.jpg'),
('PD007', 42, N'Den', 10, 'SP007', N'moon.png'),
('PD008', 40, N'Xanh den', 16, 'SP008', N'running.png'),
('PD009', 43, N'Nau', 8, 'SP009', N'moon.png'),
('PD010', 38, N'Den', 25, 'SP010', N'Yonex.png'),
('PD011', 40, N'Xanh', 30, 'SP011', N'basketball.png'),
('PD012', 41, N'Trang xanh', 17, 'SP012', N'running.png'),
('PD013', 42, N'Xam den', 9, 'SP013', N'moon.png'),
('PD014', 40, N'Trang do', 13, 'SP014', N'Yonex.png'),
('PD015', 39, N'Den trang', 19, 'SP015', N'ultraboost.jpg'),
('PD016', 41, 'White/Gold', 15, 'SP016', 'soccer.png'),
('PD017', 42, 'White/Pink', 20, 'SP017', 'Asics.png'),
('PD018', 40, 'Black', 25, 'SP018', 'Yonex.png'),
('PD019', 39, 'Beige', 30, 'SP019', 'moon.png'),
('PD020', 43, 'Blue', 12, 'SP020', 'basketball.png'),
('PD021', 42, 'White/Black', 10, 'SP021', 'running.png');
GO
-- 8. PurchaseOrder
INSERT INTO PurchaseOrder (purchaseOrderId, orderDate, employeeId, supplierId, status) VALUES
('PO001', '2026-01-05T09:15:00', 'NV005', 'NCC001', N'Da nhap'),
('PO002', '2026-01-08T10:20:00', 'NV009', 'NCC002', N'Da nhap'),
('PO003', '2026-01-12T14:10:00', 'NV013', 'NCC003', N'Da nhap'),
('PO004', '2026-01-16T08:45:00', 'NV005', 'NCC004', N'Da nhap'),
('PO005', '2026-01-20T11:30:00', 'NV009', 'NCC005', N'Da nhap'),
('PO006', '2026-02-02T09:00:00', 'NV013', 'NCC006', N'Da nhap'),
('PO007', '2026-02-06T13:25:00', 'NV005', 'NCC007', N'Da nhap'),
('PO008', '2026-02-10T15:40:00', 'NV009', 'NCC008', N'Da nhap'),
('PO009', '2026-02-14T10:05:00', 'NV013', 'NCC009', N'Da nhap'),
('PO010', '2026-02-18T08:30:00', 'NV005', 'NCC001', N'Da nhap'),
('PO011', '2026-03-01T09:50:00', 'NV009', 'NCC002', N'Da nhap'),
('PO012', '2026-03-05T14:35:00', 'NV013', 'NCC003', N'Da nhap'),
('PO013', '2026-03-09T11:15:00', 'NV005', 'NCC004', N'Da nhap'),
('PO014', '2026-03-13T16:00:00', 'NV009', 'NCC005', N'Da nhap'),
('PO015', '2026-03-17T10:45:00', 'NV013', 'NCC006', N'Da nhap');
GO

-- 9. PurchaseOrderDetail
INSERT INTO PurchaseOrderDetail (purchaseOrderId, productDetailId, quantity, importPrice) VALUES
('PO001', 'PD001', 20, 850000),
('PO002', 'PD002', 15, 1300000),
('PO003', 'PD003', 25, 650000),
('PO004', 'PD004', 18, 780000),
('PO005', 'PD005', 16, 980000),
('PO006', 'PD006', 24, 480000),
('PO007', 'PD007', 12, 900000),
('PO008', 'PD008', 20, 420000),
('PO009', 'PD009', 10, 1150000),
('PO010', 'PD010', 30, 320000),
('PO011', 'PD011', 35, 200000),
('PO012', 'PD012', 18, 720000),
('PO013', 'PD013', 12, 1350000),
('PO014', 'PD014', 15, 1050000),
('PO015', 'PD015', 22, 860000);
GO

-- 10. Invoice
INSERT INTO Invoice (invoiceId, creationDate, totalAmount, employeeId, customerId) VALUES
('HD001', '2026-03-20T09:10:00', 1250000, 'NV002', 'KH001'),
('HD002', '2026-03-21T10:25:00', 1850000, 'NV003', 'KH002'),
('HD003', '2026-03-22T11:40:00', 990000, 'NV006', 'KH003'),
('HD004', '2026-03-23T13:15:00', 1150000, 'NV007', 'KH004'),
('HD005', '2026-03-24T14:30:00', 1450000, 'NV010', 'KH005'),
('HD006', '2026-03-25T15:45:00', 750000, 'NV011', 'KH006'),
('HD007', '2026-03-26T09:20:00', 1350000, 'NV014', 'KH007'),
('HD008', '2026-03-27T10:35:00', 680000, 'NV002', 'KH008'),
('HD009', '2026-03-28T11:50:00', 1650000, 'NV003', 'KH009'),
('HD010', '2026-03-29T13:05:00', 520000, 'NV006', 'KH010'),
('HD011', '2026-03-30T14:20:00', 350000, 'NV007', 'KH011'),
('HD012', '2026-04-01T15:35:00', 1050000, 'NV010', 'KH012'),
('HD013', '2026-04-02T09:45:00', 1950000, 'NV011', 'KH013'),
('HD014', '2026-04-03T10:55:00', 1550000, 'NV014', 'KH014'),
('HD015', '2026-04-04T12:05:00', 1280000, 'NV002', 'KH015');
GO

-- 11. InvoiceDetail
INSERT INTO InvoiceDetail (invoiceDetailId, invoiceId, productDetailId, quantity, sellingPrice) VALUES
('HDD001', 'HD001', 'PD001', 1, 1250000),
('HDD002', 'HD002', 'PD002', 1, 1850000),
('HDD003', 'HD003', 'PD003', 1, 990000),
('HDD004', 'HD004', 'PD004', 1, 1150000),
('HDD005', 'HD005', 'PD005', 1, 1450000),
('HDD006', 'HD006', 'PD006', 1, 750000),
('HDD007', 'HD007', 'PD007', 1, 1350000),
('HDD008', 'HD008', 'PD008', 1, 680000),
('HDD009', 'HD009', 'PD009', 1, 1650000),
('HDD010', 'HD010', 'PD010', 1, 520000),
('HDD011', 'HD011', 'PD011', 1, 350000),
('HDD012', 'HD012', 'PD012', 1, 1050000),
('HDD013', 'HD013', 'PD013', 1, 1950000),
('HDD014', 'HD014', 'PD014', 1, 1550000),
('HDD015', 'HD015', 'PD015', 1, 1280000);
GO

-- 12. IDCounter
INSERT INTO IDCounter (prefix, currentValue) VALUES
('NV', 15),
('KH', 15),
('NCC', 15),
('CAT', 15),
('SP', 15),
('PD', 15),
('PO', 15),
('HD', 15),
('HDD', 15);
GO
