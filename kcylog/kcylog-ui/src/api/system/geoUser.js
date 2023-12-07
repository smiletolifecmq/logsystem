import request from '@/utils/request'

// 查询地理用户列表
export function listGeoUser(query) {
  return request({
    url: '/system/geoUser/list',
    method: 'get',
    params: query
  })
}

// 查询地理用户详细
export function getGeoUser(geoUserId) {
  return request({
    url: '/system/geoUser/' + geoUserId,
    method: 'get'
  })
}

// 新增地理用户
export function addGeoUser(data) {
  return request({
    url: '/system/geoUser',
    method: 'post',
    data: data
  })
}

// 修改地理用户
export function updateGeoUser(data) {
  return request({
    url: '/system/geoUser',
    method: 'put',
    data: data
  })
}

// 删除地理用户
export function delGeoUser(geoUserId) {
  return request({
    url: '/system/geoUser/' + geoUserId,
    method: 'delete'
  })
}

export function listDeptExcludeChild(userId) {
    return request({
      url: '/system/geoUser/list/exclude/' + userId,
      method: 'get'
    })
  }
