// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 POST /api/auth/login */
export async function login(body: API.LoginDTO, options?: { [key: string]: any }) {
  return request<API.RLoginResponse>('/api/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/auth/logout */
export async function logout(options?: { [key: string]: any }) {
  return request<any>('/api/auth/logout', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/auth/register */
export async function register(options?: { [key: string]: any }) {
  return request<any>('/api/auth/register', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/auth/switch-tenant/${param0} */
export async function switchTenant(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.switchTenantParams,
  options?: { [key: string]: any },
) {
  const { tenant: param0, ...queryParams } = params;
  return request<API.RLoginResponse>(`/api/auth/switch-tenant/${param0}`, {
    method: 'POST',
    params: { ...queryParams },
    ...(options || {}),
  });
}
