## 社团吧

#### 目的与背景	

​	一款协助校园社团工作的app，旨在解决大学生社团管理的问题，化繁为简，让一切在指尖触发。

​	现如今，考上一个理想的大学后，大多数学生不再把学习当作唯一的生活方式，而是参加各种各样的社团，锻炼自己的各方面能力，比如人际交往、团队协作、组织能力等，发掘自己的特长，拓展自己的人脉，在社团风靡各所学校的时候，也伴随着一系列的问题的出现，比如加入社团过程的繁琐，社团活动通知不到位，社团内部管理无门路。

​	此软件从需求分析，到概念设计，流程图，原型设计，代码研发，测试运行皆为我一人亲力亲为，欢迎大家给我提意见，一起学习进步。

#### 原型图概览
原型图网址：https://cheerqy.github.io/leagueBar/#g=1&p=引导页

![社团吧原型图](https://github.com/CheerQY/leagueBar/blob/master/readme_restory/axure_file.gif)

#### 最终成品

![成品图](https://github.com/CheerQY/leagueBar/blob/master/readme_restory/app.gif)

#### 后台支撑

​	该系统依赖的是Bmob后端云作为后台来参与开发。将数据存储在Bmob后端，需要时再获取。Bmob后台相对本地数据库要强大很多，不仅可以存文字信息，还支持文件上传，下载。

​	这个Bmob云后台很适合移动开发者做一些轻量级的应用，推荐给大家，网址：https://www.bmob.cn。

#### 需求分析

​	社团在每个大学都是一种十分普遍的团体组织，其日常工作也万变不离其宗，主要是：社团干事竞聘，社团会员统计，社团招收新会员，创业小集市，社团迎新年活动，各种活动的收取，整理。目前主要存在的问题有以下几点，一是会员管理不方便，没有系统的组织方案，社团没有凝聚力。二是缺少一个平台让所有会员参与活动的讨论与制定，导致许多会员入社后便与社团再无联系。此时，这款专门针对社团管理的社交软件应运而生，社团吧主要使用群体为各高校的社团成员，社团吧的诞生让社团管理变得方便简洁，招新与社团展示再不受开学季的时间控制，每个社团都可以随时在此软件上发布招新信息以及活动风采，也能够让社团成员通过社团内部私聊加深感情，充分融入到社团的大家庭之中。

#### 用例分析

​	使用软件的目标用户为高校的社团管理员和普通社团成员，不同的人有不同的身份，社团管理员主要是管理自己社团的各个事物，如举办活动，发送通知。社团成员享有的权利是报名参加活动，查看活动，通知消息等。社团管理员拥有社团成员的一切操作，多出了一些对社团的管理操作，如入社管理，活动通知的发布管理等。此系统的具体用例图如下：

![img](https://github.com/CheerQY/leagueBar/blob/master/readme_restory/user_case.jpg)

#### 功能说明

内容太多，现只列活动举例：

​	首页列表展示最新（热门）活动，列表项的简要信息为活动标题，活动发布时间，活动简要内容，活动浏览次数，已参加人数，发布活动社团的logo，以及活动是否正在进行的标识。

1） 最新活动：活动列表按照活动发布的日期时间倒序排列，主要是为用户实现最新活动展示，用户能及时选取自己喜欢的活动参加。

2） 热门活动：活动列表按照浏览次数倒序排列，让热门的活动更加热门。

3） 搜索活动：可根据关键字，标题，内容里的字都可作为关键字查询。

#### 系统总体设计

![img](https://github.com/CheerQY/leagueBar/blob/master/readme_restory/total_flow.jpg)

#### 实体类设计-用户类

| 字段名            | 字段类型           | 字段含义                                     |
| ----------------- | ------------------ | -------------------------------------------- |
| objectId          | String             | 用户ID                                       |
| username          | String             | 用户名，用户的登录名称                       |
| password          | String             | 用户密码，用于登录验证                       |
| mobilePhoneNumber | String             | 用户的手机号码，主要用于验证和社员之间的联系 |
| frequentContacts  | BmobRelation用户类 | 用户的常用联系人                             |
| grade             | String             | 年级                                         |
| headPic           | String             | 用户头像地址                                 |
| isInit            | Boolean            | 用户信息是否初始化                           |
| league            | 社团类             | 用户当前所在社团                             |
| major             | String             | 用户所学专业                                 |
| realName          | String             | 用户真实姓名                                 |
| qq                | String             | 用户的qq号码                                 |
| birthday          | String             | 用户的生日                                   |

#### 即时通讯部分重要代码

即时通讯功能的实现是引用的融云上的三方库，根据融云上提供的方法，进入页面时先初始化用户，getUserInfo这个方法在很多地方调用（进入页面时，点击列表的一个聊天对象聊天时，发送消息时都会调用），在融云上所有用户数据初始化完成后这个节点如果遇到调用getUserInfo方法则会更新界面上的用户列表信息：

```
/**
     * 获取融云上所有该app的用户放进friends里面，待之后获取用户信息时取用
     */
    private void initUsers() {
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.findObjects(getContext(), new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                for (MyUser user :list) {
                    String userId = user.getObjectId();
                    String name = user.getNick();
                    String portraitUri = user.getHeadPic();
                    UserInfo userInfo =  new UserInfo(userId, name, Uri.parse(portraitUri));
                    friends.add(userInfo);
                }
            }
            @Override
            public void onError(int i, String s) {
                UIHelper.ToastMessage(mActivity,"查找用户信息失败:"+s);
            }
        });
    }

    /**
     * 根据用户ID获取用户信息
     * @param userId
     * @return
     */
    @Override
    public UserInfo getUserInfo(String userId) {
        for (UserInfo user : friends) {
            if (user.getUserId().equals(userId)){
                return user;
            }
        }
        return null;
    }
```

#### 导航页引导动画

实现此引导动画会用到两个重要的布局，一是百分比布局，一个可以根据手机屏幕大小进行百分比展示的布局。另外一个重要的布局是viewpager，实现页面的切换，并在切换时做一些漂亮的转场动画。

在下面的方法监听页面的滑动事件，实现时差动画。对于viewpager，页面滑动方向和滑动距离，表示在transformPage方法的参数中。滑动时实现的白色手机框缩放和部门内部图标位移动画在下面这个方法中做了详细解释：

```
   /**
     * 抽象方法
     * @param page 当前页面
     * @param position (-1~1)
     */
    @Override
    public void transformPage(View page, float position) {
        //监听页面的滑动事件，实现视差动画
        if (position<1&&position>-1){
            //1、实现视差动画,白色手机框框背景，top_phone_container
            //2、部分内部的图标进行位移动画
            ViewGroup rl= (ViewGroup) page.findViewById(R.id.rl);
            for (int i=0;i<rl.getChildCount();i++){
                //遍历子控件，进行内部图标动画，进行位移动画
                View child=rl.getChildAt(i);
                //设置左右位移的距离
                float factor= (float) (Math.random()*2);
                //随机数只随机一次
                if (child.getTag()==null){
                    //说明还没有随机数
                    child.setTag(factor);
                }else {
                    factor= (float) child.getTag();
                }
                if (child.getId()==R.id.top_phone_container){
                    //说明是白色的框框
                    continue;
                }
                //进行位移动画
                child.setTranslationX(position*factor*child.getWidth());
            }
            //白色框框进行动画,进行缩小动画
            ViewGroup top_phone_container= (ViewGroup) page.findViewById(R.id.top_phone_container);
            //position 0~1
            top_phone_container.setScaleX(Math.max(0.6f,1- Math.abs(position)));
            top_phone_container.setScaleY(Math.max(0.6f,1- Math.abs(position)));
            for (int i=0;i<top_phone_container.getChildCount();i++){
                //框框里面的位移动画
                View child=top_phone_container.getChildAt(i);
                float factor= (float) (1+ Math.random());
                if (child.getTag()==null){
                    child.setTag(factor);
                }else {
                    factor= (float) child.getTag();
                }
                if (i%2==0) {
                    child.setTranslationX(position * factor * child.getWidth());
                }else {
                    child.setTranslationX(-position*factor*child.getWidth());
                }

            }
        }
    }
```

#### 城市三级联动部分重要代码

这部分用到的知识有解析XML数据，和一个三方的联动视图。首先将城市的所有数据录入到xml文件中，作为assets放到项目中，然后在选择城市的地方解析xml文件到可以联动的三方联动视图WheelView中。根据这个知识，后面选择专业的时候，也用到这个方法来进行学院-专业的联动选择。如下为主要代码

```
/**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas(Context context) {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = context.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                   
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
        }
} 
```

#### 写在最后

此软件是我在本科学校的毕业设计，从需求->概念->流程图->原型->数据库设计->代码->测试，经历了半年左右的时间，期间遇到困难，通过在网上学习，请教实习公司老员工解决问题，获得了指导老师和同事的一致好评，给出的高度评价是：一个稍作修改就可以上线的app。很开心努力得到了回报，希望在以后的职业生涯也能带着这份激情，一路向前。
