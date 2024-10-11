import request from '@/utils/request'

// 查询责权发生制开票明细列表
export function listInfo(query) {
  return request({
    url: '/system/responsibilitiesInfo/list',
    method: 'get',
    params: query
  })
}

// 查询责权发生制开票明细详细
export function getInfo(infoId) {
  return request({
    url: '/system/responsibilitiesInfo/' + infoId,
    method: 'get'
  })
}

// 新增责权发生制开票明细
export function addInfo(data) {
  return request({
    url: '/system/responsibilitiesInfo',
    method: 'post',
    data: data
  })
}

// 修改责权发生制开票明细
export function updateInfo(data) {
  return request({
    url: '/system/responsibilitiesInfo',
    method: 'put',
    data: data
  })
}

// 删除责权发生制开票明细
export function delInfo(infoId) {
  return request({
    url: '/system/responsibilitiesInfo/' + infoId,
    method: 'delete'
  })
}
