import request from '@/utils/request'

// 查询客户信息列表
export function listClientele(query) {
  return request({
    url: '/system/clientele/list',
    method: 'get',
    params: query
  })
}

// 查询客户信息详细
export function getClientele(id) {
  return request({
    url: '/system/clientele/' + id,
    method: 'get'
  })
}

// 新增客户信息
export function addClientele(data) {
  return request({
    url: '/system/clientele',
    method: 'post',
    data: data
  })
}

// 修改客户信息
export function updateClientele(data) {
  return request({
    url: '/system/clientele',
    method: 'put',
    data: data
  })
}

// 删除客户信息
export function delClientele(id) {
  return request({
    url: '/system/clientele/' + id,
    method: 'delete'
  })
}

export function deptTreeSelect() {
  return request({
    url: '/system/clientele/deptTree',
    method: 'get'
  })
}
