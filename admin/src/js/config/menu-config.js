const menuConfig = [
  {
    title: '课表',
    key: 'Home',
    icon: 'icon-clipboard',
  },
  {
    title: '成绩',
    key: 'Score',
    icon: 'icon-paper'
  },
  {
    title: '系统设置',
    key: 'SysSetting',
    icon: 'icon-cog',
    children: [
      {
        title: '个人中心',
        key: 'AccountBasic'
      },
      {
        title: '安全设置',
        key: 'SecuritySetting'
      }
    ]
  },
  {
    title: '异常页面',
    key: 'ErrorPages',
    icon: 'icon-circle-cross',
    children: [
      {
        title: '403',
        key: 'PermissionError'
      },
      {
        title: '404',
        key: 'NotfoundError'
      },
      {
        title: '500',
        key: 'SystemError'
      }
    ]
  }
];

export default menuConfig;
