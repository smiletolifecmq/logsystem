import request from '@/utils/request'

// 查询部门日常列表
export function listDaily(query) {
  return request({
    url: '/system/departmentDaily/list',
    method: 'get',
    params: query
  })
}

// 查询部门日常详细
export function getDaily(dailyId) {
  return request({
    url: '/system/departmentDaily/' + dailyId,
    method: 'get'
  })
}

// 新增部门日常
export function addDaily(data) {
  return request({
    url: '/system/departmentDaily',
    method: 'post',
    data: data
  })
}

// 修改部门日常
export function updateDaily(data) {
  return request({
    url: '/system/departmentDaily',
    method: 'put',
    data: data
  })
}

// 删除部门日常
export function delDaily(dailyId) {
  return request({
    url: '/system/departmentDaily/' + dailyId,
    method: 'delete'
  })
}
