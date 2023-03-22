
package com.example.sp23_pro1121_cp18103_group4.data;

public  class Data {

 public static final String insertNhanVien=" insert into NhanVien(name,user,passWord,numberPhone,gioiTinh,ngaySinh,uyQuyen,status)" +
         "values  ('admin','admin','admin','000000000','nu','1/1/2003','NhanVien','Dang Lam Viec')," +
         "('Bac','bac','bac','0359789536','nam','1/1/2003','NhanVien','Dang Lam Viec')," +
         "('hoang','hoang','hoang','0111111111','nam','1/1/2003','NhanVien','Dang Lam Viec')," +
         "('Long','long','long','03333333','nam','1/1/2003','NhanVien','Tam nghi')," +
         "('Son','son','son','022221111','nam','1/1/2003','NhanVien','Dang Lam Viec');";

 public static final  String insertLoaiMon="insert into LoaiMon(tenLoaiMon,imgLoaiMon)" +
         "values ( 'Tra sua','null')," +
         "('Pizza','null')," +
         "('Dessert','null');";

 public static final  String insertMon="insert into Mon(tenMon,giaTien,trangthai,maLoaiMon,imgMon)" +
         "values ('Tra sua Chan Chau','10000','đang có','1','null')," +
         "('Tra sua QIQI','10000','đang có','1','null')," +
         "('Tra sua Nóng','10000','hết hàng','1','null')," +
         "('Pizza cay','100000','dang co','2','null')," +
         "('Pizza full topping','120000','dang co','2','null')," +
         "('Pizza phap','20000','dang co','2','null')," +
         "('Dessert cay','100000','dang co','3','null')," +
         "('Dessert 1','100000','dang co','3','null')," +
         "('Dessert 23','100000','dang co','3','null');";
 public static final String insertBan="insert into Ban(tenBan,status)" +
         "values('1','null')," +
         "('1','null')," +
         "('2','null')," +
         "('3','null')," +
         "('4','null')," +
         "('5','null')," +
         "('6','null')," +
         "('7','null')," +
         "('8','null')," +
         "('9','null');";

 public static  final  String insertMonTrongBan="insert into MontrongBan(maBan,maMon,soLuong) " +
         "values('1','2','2')," +
         "('1','2','2')," +
         "('1','2','2')," +
         "('2','3','2')," +
         "('2','5','4')," +
         "('3','3','4')," +
         "('4','7','4')," +
         "('5','9','4')," +
         "('7','9','4')," +
         "('9','1','4');";

 public static  final  String insertHoaDon="insert into HoaDon(maBan,maNV,ngayLap,maKhachHang,tongTien) " +
         "values ('2','2','18/3/2023','1','560000')," +
         "('2','2','18/3/2023','1','560000')," +
         "('6','3','18/4/2023','1','560000')," +
         "('9','3','10/5/2023','2','560000')," +
         "('1','5','08/3/2023','1','560000');" ;
//v
 public static  final  String insertKhachHang="insert into KhachHang(name,numberPhone,diaChi)" +
         "values('tran Van A','035965534','A')," +
         "('Nguyen Van B','0399995533','B')," +
         "('tran Van C','0311111535','C');";
}


