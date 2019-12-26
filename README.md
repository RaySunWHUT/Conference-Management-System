### **声明(Statement)**：

This is the course design of database of WHUT(Wuhan University Of Technology). This project is one of them which named "Conference Management System(CMS)". I open the source code of it. This is the achievement of the CMS. You can enjoy it. No thanks, but give me star!

The follow is the problem exists in this project, I will solve them gradually.

前端：front end

1. 忘记密码？  未实现
2. 系统只有管理员账号，无注册功能。
3. **用户通知**为额外自增功能，非课程实验要求。
4. 添加会议时，若会议名为空，提醒后，填写框会自动消失。
5. 各个界面的翻页功能未实现，会有bug。(ps：少量数据记录时，并不影响使用)
6. 会议管理(数据未持久化)：修改界面弹出后，可以回显数据，但若此时刷新界面，数据会消失。
7. 填写纪要界面，现实的更新时间(updateTime)格式不正确。
8. 修改密码未实现。
9. state未保存对象，而是保存对象属性，导致代码赘余。
10. 前端整体架构未完全实现，axios等未进行封装。

后端：back end

	1. 未对logout功能做进一步处理。

 	2. 未对Constants和JSONObject规范化处理。