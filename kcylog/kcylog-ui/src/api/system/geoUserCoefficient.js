import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listCoefficient(query) {
  return request({
    url: '/system/geoUserCoefficient/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getCoefficient(coefficientId) {
  return request({
    url: '/system/geoUserCoefficient/' + coefficientId,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addCoefficient(data) {
  return request({
    url: '/system/geoUserCoefficient',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateCoefficient(data) {
  return request({
    url: '/system/geoUserCoefficient',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delCoefficient(coefficientId) {
  return request({
    url: '/system/geoUserCoefficient/' + coefficientId,
    method: 'delete'
  })
}
