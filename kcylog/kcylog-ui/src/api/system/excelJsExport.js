import ExcelJS from "exceljs";
import { saveAs } from "file-saver"; // 引入 file-saver 库

// 扁平化函数
function flatten(obj, prefix = '') {
  let result = {};
  for (let key in obj) {
    if (obj.hasOwnProperty(key)) {
      const prop = obj[key];
      const newKey = prefix ? `${prefix}.${key}` : key;
      if (typeof prop === 'object' && prop !== null) {
        // 如果是对象，递归调用 flatten
        Object.assign(result, flatten(prop, newKey));
      } else {
        // 否则直接赋值
        result[newKey] = prop;
      }
    }
  }
  return result;
}

/**
 * excel 导出
 * style:excel表的样式配置
 * tableData:表的数据内容
 * headerColumns:表头配置
 * sheetName：工作表名
 */
export function excelJsExport(sheetName, headerColumns, tableData) {
  // 创建工作簿
  const workbook = new ExcelJS.Workbook();
  workbook.creator = '项目移交信息';
  workbook.created = new Date();
  
  // 添加工作表
  const worksheet = workbook.addWorksheet(sheetName);
  
  if (headerColumns.length > 0) {
    // 设置列头
    const columnsData = headerColumns.map((column, index) => {
      const width = column.width;
      return {
        header: column.title,
        key: column.dataIndex,
        width: isNaN(width) ? 20 : width / 10
      };
    });
    worksheet.columns = columnsData;
    // 设置表头样式
  }

  // 设置行数据
  if (tableData.length > 0) {
    // 将传入的数据格式化为exceljs可使用的数据格式
    const data = [];
    tableData.forEach((table) => {
      let obj = {};
      const tableFlat = flatten(table);
  
      headerColumns.forEach((header) => {
        obj[header.dataIndex] = tableFlat[header.dataIndex]; // 直接使用扁平化数据
      });
      data.push(obj);
    });
    
    // 添加行数据到工作表
    worksheet.addRows(data);
  }
  
  // 生成 Excel 文件并下载
  return workbook.xlsx.writeBuffer().then((buffer) => {
    const blob = new Blob([buffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    saveAs(blob, `${sheetName}.xlsx`); // 使用 FileSaver 下载文件
  });
}
