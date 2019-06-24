![img](https://github.com/rabbit860321/Simple_Accounting_App/blob/master/app/src/main/res/drawable/logo.png)
# 簡易記帳APP ![img](https://github.com/rabbit860321/Simple_Accounting_App/blob/master/app/src/main/res/mipmap-hdpi/icon.png)
此APP能夠記錄使用者的收支出，並能快速地查看使用者所有帳戶的目前金額  
新增支出入時，預設的分類若無想要的，可自行新增  
可查看每個月的歷史支出，與基本的支出統計  
[APK檔](https://drive.google.com/open?id=16aNGnus0ZUeZaWBxGl1nNRSmbMab-Y1p )  <= 無毒請服用  

## logo頁面
logo頁面有一個簡單的判斷，如使用者沒輸入過帳戶資料，會跳轉到設定帳戶資料頁面，  
若有一筆以上的帳戶資料，會跳轉到主頁面  
![img](https://github.com/rabbit860321/Simple_Accounting_App/blob/master/LOGODEMO.gif)  

## 設定帳戶頁面
設定帳戶頁面裡按下"+"號會跳出新增帳戶視窗，輸入帳戶名稱與金額按下確認，新增完成  
若金額輸入錯誤，點選欲修改的帳戶欄位，會跳出修改帳戶視窗，修改金額按下確認，修改完成  
![img](https://github.com/rabbit860321/Simple_Accounting_App/blob/master/SETTINGDEMO.gif)  
(*若新增帳戶時 帳戶名稱重複，會跳出"帳戶名稱重複!"提示訊息)  
(*若無輸入金額 會跳出"請輸入金額!"提示訊息)

## 主畫面
![img](https://github.com/rabbit860321/Simple_Accounting_App/blob/master/MAINDEMO.jpg)  
* 新增一筆支出  
點選右上角的"+"號，選擇"支出"，會跳轉到選擇支出類別頁面，點選左邊的主類別，再點選右邊的副類別，跳轉到確認頁面  
輸入金額 選擇帳戶 輸入備註(非必要)，按下"完成"新增支出成功，帳戶金額更新  
*若無輸入金額 會跳出"請輸入金額!"提示訊息*   
*若該帳戶餘額不足 會跳出"你不夠錢啦!"提示訊息*  
![img](https://github.com/rabbit860321/Simple_Accounting_App/blob/master/COSTDEMO.gif)  
* 若副類別無適合的 可自行新增
若想刪除 長按該副類別即會跳出確認刪除視窗   
![img](https://github.com/rabbit860321/Simple_Accounting_App/blob/master/COST1DEMO.gif)  
* 常用支出  
在支出確認頁面勾選常用支出，點選主畫面右下角愛心圖示即可看見新增的常用支出  
![img](https://github.com/rabbit860321/Simple_Accounting_App/blob/master/FAVDEMO.gif)  
* 收入
點選右上角的"+"號，選擇"收入"  
選擇帳戶 輸入金額  
![img](https://github.com/rabbit860321/Simple_Accounting_App/blob/master/INCOMEDEMO.jpg)  
* 轉帳
點選右上角的"+"號，選擇"轉帳"  
選擇轉出帳戶 選擇轉入帳戶 輸入金額  
![img](https://github.com/rabbit860321/Simple_Accounting_App/blob/master/TRANDEMO.jpg)  
