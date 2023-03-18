package com.example.sp23_pro1121_cp18103_group4.data;

public  class Data {

 public static final String insertNhanVien=" insert into NhanVien(name,user,passWord,email,numberPhone,status)" +
         "values  ('admin','admin','admin','admin',so dien thoai,'Dang Lam Viec')," +
         "('Bac','bac','bac','bactxph20234@fpt.edu.vn',0359789536,'Dang Lam Viec')," +
         "('hoang','hoang','hoang','hoang@fpt.edu.vn',0111111111,'Dang Lam Viec')," +
         "('Long','long','long','Long@fpt.edu.vn',03333333,'Tam nghi')," +
         "('Son','son','son','son@fpt.edu.vn',022221111,'Dang Lam Viec');";
 public static final  String insertLoaiMon="insert into LoaiMon(tenLoaiMon,imgLoaiMon)" +
         "values ( 'Tra sua','null')," +
         "('Pizza','null')," +
         "('Dessert','null');";

 public static final  String insertMon="insert into Mon(ten,giaTien,status,maLoaiMon,imgMon)" +
         "values ( 'Tra sua Chan Chau',''10000','null','đang có,'1','null')," +
         "( 'Tra sua QIQI',''10000','null','đang có,'1','null')," +
         "( 'Tra sua Nóng',''10000','null','hết hàng,'1','null')," +
         "('Pizza cay','100000','null','dang co','2','null')," +
         "('Pizza full topping','120000','null','dang co','2','null')," +
         "('Pizza phap','20000','null','dang co','2','null')," +
         "('Dessert cay','100000','null','dang co','3','null')" +
         "('Dessert 1','100000','null','dang co','3','null')," +
         "('Dessert 23','100000','null','dang co','3','null');";
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
         "('9','1','4'),";

 public static  final  String insertHoaDon="insert into HoaDon(maBan,maNV,ngayLap,maKH,tongTien) " +
         "values ('2','2',18/3/2023','1','560000')," +
         "('2','2',18/3/2023','1','560000')," +
         "('6','3',18/4/2023','1','560000')," +
         "('9','3',10/5/2023','2','560000')," +
         "('1','5',08/3/2023','1','560000');" ;

 public static  final  String insertKhachHang="insert into KhachHang(tenKH,email,numberPhone)" +
         "values('tran Van A','a@gmail.com','035965534')," +
         "('Nguyen Van B','B@gmail.com','0399995533')," +
         "('tran Van C','C@gmail.com','0311111535');";
}
