/**
 * @see https://umijs.org/docs/max/access#access
 * */
export default function access(initialState: { currentUser?: API.DynamicSysUser } | undefined) {
  const { currentUser } = initialState ?? {};
  return {
    roleAdmin: currentUser && currentUser.roles?.filter(role => role.code === 'admin'),
  };
}
