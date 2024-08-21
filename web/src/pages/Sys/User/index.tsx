import { findSysPostsList } from '@/services/sys/sysPostsController';
import { findSysUserPage } from '@/services/sys/sysUserController';
import { PageContainer, ProColumns, ProTable } from '@ant-design/pro-components';
import { Avatar, Tag } from 'antd';
import { undefined } from '@umijs/utils/compiled/zod';

const App = () => {
  const columns: ProColumns<API.SysUserPageView>[] = [
    {
      title: '用户名',
      dataIndex: 'username',
    },
    {
      title: '邮箱',
      dataIndex: 'email',
    },
    // gender
    {
      title: '性别',
      dataIndex: 'gender',
      valueEnum: {
        MALE: {
          text: '男',
        },
        FEMALE: {
          text: '女',
        },
      },
    },
    // status
    {
      title: '状态',
      dataIndex: 'status',
      valueEnum: {
        true: {
          text: '正常',
          status: 'Success',
        },
        false: {
          text: '禁用',
          status: 'Error',
        },
      },
    },
    // 角色
    {
      title: '角色',
      dataIndex: 'roles',
      render: (text, record) => record.roles?.map((item) => <Tag key={item.id}>{item.name}</Tag>),
    },
    // 部门
    {
      title: '部门',
      dataIndex: 'department',
      render: (text, record) => <Tag>{record.department?.name}</Tag>,
      hideInSearch: true,
    },
    // 岗位
    {
      title: '岗位',
      dataIndex: 'posts',
      render: (text, record) => <Tag>{record.posts?.name}</Tag>,
      valueType: 'select',
      formItemProps: {
        name: 'postsId'
      },
      request: async () => {
        const { data } = await findSysPostsList({ specification: {} });
        return data?.map((item) => ({ label: item.name, value: item.id })) ?? [];
      },
    },
    // phoneNumber
    {
      title: '手机号',
      dataIndex: 'phoneNumber',
    },
    // 头像
    {
      title: '头像',
      dataIndex: 'avatar',
      key: 'avatar',
      render: (text, record) => <Avatar src={record.avatar} />,
      hideInSearch: true,
    },
    // 操作
    {
      title: '操作',
      valueType: 'option',
      dataIndex: 'option',
      render: (text, record) => [

      ],
    },
  ];

  return (
    <>
      <PageContainer title="系统用户管理">
        <ProTable<API.SysUserPageView, API.SysUserPageSpecification>
          columns={columns}
          request={async (params, sort, filter) => {
            const res = await findSysUserPage({
              specification: {
                ...params,
              },
              pageable: {
                currentPage: params.current ?? 1,
                pageSize: params.pageSize ?? 10,
                sortProperty: "id",
                sortDirection: "DESC",
              }
            });
            return {
              data: res.data,
              success: true,
              total: res.total,
            };
          }}
        />
      </PageContainer>
    </>
  );
};

export default App;
