SCP Android 第三方客户端

`kotlin`

# game list
- 以撒
- distrust
- 幽浮
- 绣湖/ 逃离方块
- 恶灵附身
- 艾迪芬奇的记忆
- 克苏鲁的呼唤：官方游戏

# scp game list
(【SCP】器关的彷徨 【SCP】器関ノ彷徨 -The will of a single Tale-【DEMOver.】)[https://store.steampowered.com/app/836940/SCP_The_will_of_a_single_TaleDEMOver/]
SCP：秘密实验室 https://store.steampowered.com/app/700330/SCP_Secret_Laboratory/
Daughter of Shadows: An SCP Breach Event https://store.steampowered.com/app/449820/?utm_source=indienova

---

# v0.0.1
预计开发完001-4999系列的目录，标题和链接都很简单不涉及数据库存储和网络加载，阅读直接用webView打开链接

TODO 8.23
- [x] 整理adapter基类
- [x] 整理scp网站的内容结构，为之后划分板块做准备 （PNG已上传)
- [x] toolbar展开菜单theme修改
- [x] 整理笔记


# v0.0.2
发现原来scp系列也还是有标题，看来需要重抓了
数据来自爬虫抓取，存储在bmob平台，自己的api和key是私下存储的在PrivateConstants这个类里
需要尝试这个开源项目的可以自己创建一个bmob账号然后把数据（后续会把爬虫和数据都整理一份上传）传到后台，填上自己的key：
```
object PrivateConstants {
    const val APP_ID = "xxx"
    const val API_KEY = "xxx"
}
```

- [x] 搭建网络请求框架
- [x] 完成基本数据库存储功能
- [x] 重新抓取scp系列，抓取标题
- [x] 添加一个反转列表功能

爬虫及数据已上传

# v0.0.3
梳理全站架构，完成scp系列部分的开发，√表示内容抓取也已完成
- [x] SCP系列
  - [x] SCP系列 √
  - [x] SCP-CN系列 √
  - [x] SCP故事版 √
  - [x] 归档内容 √
  - [x] 相关信息 √

# v0.0.4
开发scp图书馆部分
- [x] SCP图书馆
  - [x] 基金会故事/CN
  - [x] 故事系列/CN
  - [x] 设定中心/CN
  - [x] 征文比赛/CN

# v0.0.5
- [x] 修改数据表结构，腾出空间
- [x] 检测更新功能
- [x] 修改初始加载逻辑，避免每次加载时间过长

# v0.0.6
离线阅读，夜间模式
- [x] 添加事故记录部分内容
- [x] 添加中国分部原创故事（按时间排序）
- [x] 添加数据加载的可选项
- [x] 优化数据存取过程，减少卡顿
- [x] 添加阅读页面的可选功能
  - [x] 切换阅读模式
  - [x] 反馈问题
- [x] 文案修改
- [x] 升级测试通过
- [x] 夜间模式
- [x] 页面内本地跳转

# v0.0.7
功能添加
- [x] 搜索功能（标题搜索）
- [x] 随机文档
- [x] 收藏
- [x] 读过
- [x] 正文阅读滑动到底部时弹出上下篇
- [x] 重新抓数据并解决以下问题
  - [x] 404页面添加标识，在目录页就展示出来
  - [x] 为了统一竞赛页等特殊页面的全链接，对链接进行统一处理
- [x] 积分系统准备工作
- [x] 数据更新信息显示

# v0.0.8
功能优化
- [x] 减少目录条目数量 500->200
- [x] 正文按钮位置调整，toolbar可收缩
- [x] sdk更新，fragment替换成v4版本
- [x] 等级和积分系统
- [x] 导航返回键返回之前打开的文章
- [x] 优化正文中折叠条目的操作
- [x] 用户调查问卷
- [x] 取消已读功能

# v0.0.9
- [ ] 名片分享功能
- [ ] UI优化
- [ ] 游戏相关链接整理汇总
- [x] 折叠功能优化

# 后续
- [ ] 阅读体验优化
 - [ ] 主题/换肤
- [ ] 最新
  - [ ] 最近更新/修改/新增的条目
  - [ ] 随机[原创]scp/故事
  - [ ] 评分最高/最低
- [ ] 更多内容
  - [ ] 放逐者图书馆
  - [ ] 国际版
- [ ] 其他
  - [ ] 写作相关
  - [ ] 新人资讯
  - [ ] 设置
