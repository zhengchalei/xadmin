
// 存储token
export const setToken = (token: string) => {
  localStorage.setItem('token', token);
};

export const getToken = () => {
  return localStorage.getItem('token');
};

export const removeToken = () => {
  localStorage.removeItem('token');
};

// hasToken
export const hasToken = () => {
  return !!getToken();
};
