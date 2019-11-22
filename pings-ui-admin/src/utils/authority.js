// use localStorage to store the authority info, which might be sent from server in actual project.
export function getAuthority(str) {
  // return localStorage.getItem('antd-pro-authority') || ['admin', 'user'];
  const authorityString =
    typeof str === 'undefined' ? localStorage.getItem('antd-pro-authority') : str;
  // authorityString could be admin, "admin", ["admin"]
  let authority;
  try {
    authority = JSON.parse(authorityString);
  } catch (e) {
    authority = authorityString;
  }
  if (typeof authority === 'string') {
    return [authority];
  }
  return authority || ['guest'];
}

export function setAuthority(authority) {
  const proAuthority = typeof authority === 'string' ? [authority] : authority;
  return localStorage.setItem('antd-pro-authority', JSON.stringify(proAuthority));
}

//**获取权限认证token */
export function getAuthorization() {
  return localStorage.getItem('authorization');
}

//**保存权限认证token */
export function setAuthorization(token) {
  const oldTeken = getAuthorization();
  if (!oldTeken || oldTeken !== token) localStorage.setItem('authorization', token);
}

//**删除权限认证token */
export function removeAuthorization() {
  localStorage.removeItem('authorization');
}
