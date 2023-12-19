import request from '@/utils/request'

// 查询系数存档列表
export function listProfile(query) {
  return request({
    url: '/system/geoCoefficientProfile/list',
    method: 'get',
    params: query
  })
}

// 查询系数存档详细
export function getProfile(profileId) {
  return request({
    url: '/system/geoCoefficientProfile/' + profileId,
    method: 'get'
  })
}

// 新增系数存档
export function addProfile(data) {
  return request({
    url: '/system/geoCoefficientProfile',
    method: 'post',
    data: data
  })
}

// 修改系数存档
export function updateProfile(data) {
  return request({
    url: '/system/geoCoefficientProfile',
    method: 'put',
    data: data
  })
}

// 删除系数存档
export function delProfile(profileId) {
  return request({
    url: '/system/geoCoefficientProfile/' + profileId,
    method: 'delete'
  })
}
