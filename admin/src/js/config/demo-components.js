export default [ {
  path: '/account-basic',
  name: 'AccountBasic',
  component: (resolve) => require(['components/demo-components/account/account'], resolve),
  meta: { title: '个人中心', icon: 'icon-head' }
}, {
  path: '/account-setting/',
  name: 'AccountSetting',
  component: (resolve) => require(['components/demo-components/account/account-setting'], resolve),
  children: [{
    path: 'security-setting',
    name: 'SecuritySetting',
    component: (resolve) => require(['components/demo-components/account/modules/security-setting'], resolve),
    meta: { title: '安全设置' }
  }, {
    path: 'notice-setting',
    name: 'NoticeSetting',
    component: (resolve) => require(['components/demo-components/account/modules/notice-setting'], resolve),
    meta: { title: '通知设置' }
  }],
  meta: { title: '个人设置' }
}];
