# 說明
<hr>
<h2>-----2019/04/22 - bug-----</h2> 
<ul>
  <li>2019/04/26新增:帳戶初始化頁面中，新增帳戶若無輸入金額，會發生錯誤</li>
　<li>帳戶初始化頁面中->新增帳戶的視窗 點選金額的edittext 鍵盤應該跳出數字鍵盤 應該也只能輸入數字~</li>
　<li>帳戶初始化頁面中若輸入錯誤想刪除 點擊一次該item彈出刪除 好像怪怪的 長按?往右滑?</li>
　<li>主頁面 點選新增一筆->輸入金額那的edittext要設定成不給自行輸入嗎? 只用該畫面的數字鍵盤~</li>
  <li>輸入完金額 按下OK 若不點左邊list 也不點右邊list 
        直接點+編輯 按下確定 會出現nullpointer (應該要用例外處理 我不太懂例外 QQ</li>
  <li>輸入完金額->選完類別->進入確認頁面 若想重選類別 目前是要點2次edittext才能回到選擇類別頁面 好像點1次比較直覺~</li>
  <li>新增一筆資料後，回到主畫面，若點下手機的返回鍵(我試的手機是有返回鍵啦~)，好像有點邏輯錯誤~</li>
</ul>
<hr>

<p>第一次開啟APP，必須先設定當前帳戶資料
  <img src="https://raw.githubusercontent.com/rabbit860321/NKUST_SE/master/%E8%AA%AA%E6%98%8E%E5%9C%96%E7%89%87/%E5%B8%B3%E6%88%B6%E5%88%9D%E5%A7%8B%E5%8C%96.jpg" width="30%" height="30%">按下"+"號新增
</p>

<p>
  <img src="https://github.com/rabbit860321/NKUST_SE/blob/master/%E8%AA%AA%E6%98%8E%E5%9C%96%E7%89%87/%E6%96%B0%E5%A2%9E%E5%B8%B3%E6%88%B6.jpg" width="30%" height="30%">
  按下儲存->
  <img src="https://github.com/rabbit860321/NKUST_SE/blob/master/%E8%AA%AA%E6%98%8E%E5%9C%96%E7%89%87/%E5%B8%B3%E6%88%B6%E6%96%B0%E5%A2%9E%E5%AE%8C%E6%88%90.jpg" width="30%" height="30%">
  若輸入錯誤，點擊該item
</p>

<p>
  <img src="https://github.com/rabbit860321/NKUST_SE/blob/master/%E8%AA%AA%E6%98%8E%E5%9C%96%E7%89%87/%E7%A2%BA%E5%AE%9A%E5%88%AA%E9%99%A4%E9%83%B5%E5%B1%80.jpg" width="30%" height="30%">
  按下確定，刪除->
  <img src="https://github.com/rabbit860321/NKUST_SE/blob/master/%E8%AA%AA%E6%98%8E%E5%9C%96%E7%89%87/%E5%88%AA%E9%99%A4%E5%AE%8C%E6%88%90.jpg" width="30%" height="30%">按下完成，進入主頁面
</p>
<p>
  <img src="https://github.com/rabbit860321/NKUST_SE/blob/master/%E8%AA%AA%E6%98%8E%E5%9C%96%E7%89%87/%E4%B8%BB%E7%95%AB%E9%9D%A2.jpg" width="30%" height="30%">按下"新增一筆"，新增支出/收入
  <img src="https://github.com/rabbit860321/NKUST_SE/blob/master/%E8%AA%AA%E6%98%8E%E5%9C%96%E7%89%87/%E9%A0%90%E8%A8%AD%E6%94%AF%E5%87%BA.jpg" width="30%" height="30%">點選餐飲
</p>
<p>
  <img src="https://github.com/rabbit860321/NKUST_SE/blob/master/%E8%AA%AA%E6%98%8E%E5%9C%96%E7%89%87/%E9%BB%9E%E9%81%B8%E9%A0%85%E7%9B%AE.jpg" width="30%" height="30%">
  若無想選的類別，按下+編輯可以新增
  <img src="https://github.com/rabbit860321/NKUST_SE/blob/master/%E8%AA%AA%E6%98%8E%E5%9C%96%E7%89%87/%E8%BC%B8%E5%85%A5%E9%A3%B2%E6%96%99.jpg" width="30%" height="30%">
</p>
<p>
  <img src="https://github.com/rabbit860321/NKUST_SE/blob/master/%E8%AA%AA%E6%98%8E%E5%9C%96%E7%89%87/%E5%AE%8C%E6%88%90%E6%96%B0%E5%A2%9E%E9%A3%B2%E6%96%99.jpg" width="30%" height="30%">按下"飲料"->進入確認頁面
  <img src="https://github.com/rabbit860321/NKUST_SE/blob/master/%E8%AA%AA%E6%98%8E%E5%9C%96%E7%89%87/%E7%A2%BA%E8%AA%8D%E9%A0%81%E9%9D%A2.jpg" width="30%" height="30%">
</p>
<p>
  能自行輸入備註<img src="https://github.com/rabbit860321/NKUST_SE/blob/master/%E8%AA%AA%E6%98%8E%E5%9C%96%E7%89%87/%E5%82%99%E8%A8%BB.jpg" width="30%" height="30%">點完成，儲存
  <img src="https://github.com/rabbit860321/NKUST_SE/blob/master/%E8%AA%AA%E6%98%8E%E5%9C%96%E7%89%87/%E9%A1%AF%E7%A4%BA%E4%BB%8A%E6%97%A5.jpg" width="30%" height="30%">
</p>
