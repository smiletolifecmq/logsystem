import request from '@/utils/request'

// 查询地理员工请假列表
export function listHoliday(query) {
  return request({
    url: '/system/geoHoliday/list',
    method: 'get',
    params: query
  })
}

// 查询地理员工请假详细
export function getHoliday(holidayId) {
  return request({
    url: '/system/geoHoliday/' + holidayId,
    method: 'get'
  })
}

// 新增地理员工请假
export function addHoliday(data) {
  return request({
    url: '/system/geoHoliday',
    method: 'post',
    data: data
  })
}

// 修改地理员工请假
export function updateHoliday(data) {
  return request({
    url: '/system/geoHoliday',
    method: 'put',
    data: data
  })
}

// 删除地理员工请假
export function delHoliday(holidayId) {
  return request({
    url: '/system/geoHoliday/' + holidayId,
    method: 'delete'
  })
}
