import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listBcyj(query) {
  return request({
    url: '/system/bcyj/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getBcyj(projectCode) {
  return request({
    url: '/system/bcyj/' + projectCode,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addBcyj(data) {
  return request({
    url: '/system/bcyj',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateBcyj(data) {
  return request({
    url: '/system/bcyj',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delBcyj(projectCode) {
  return request({
    url: '/system/bcyj/' + projectCode,
    method: 'delete'
  })
}
