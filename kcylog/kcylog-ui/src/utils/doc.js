import docxtemplater from 'docxtemplater'
import PizZip from 'pizzip'
import JSZipUtils from 'jszip-utils'
import { saveAs } from 'file-saver'

export const exportDocx = (tempDocxPath, data, fileName) => {
    // 读取并获得模板文件的二进制内容
    JSZipUtils.getBinaryContent(tempDocxPath, (error, content) => {
        if (error) {
            throw error
        }
        let opts = {};
        opts.centered = false;
        opts.getImage = function (tagValue) {
            return new Promise(function (resolve, reject) {
                JSZipUtils.getBinaryContent(tagValue, function (error, content) {
                    if (error) {
                        return reject(error);
                    }
                    return resolve(content);
                });
            });
        };
      
        // 创建一个JSZip实例，内容为模板的内容
        const zip = new PizZip(content)
        // 创建并加载docxtemplater实例对象
        const doc = new docxtemplater().loadZip(zip).compile();
        doc.resolveData({ ...data }).then(() => {
            doc.render()
            const out = doc.getZip().generate({
                type: 'blob',
                mimeType: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
            }) // Output the document using Data-URI
            saveAs(out, fileName)
        }).catch(err => console.log('errorsss', err))
    })
}

  /**
     导出docx
     @param { String } tempDocxPath 模板文件路径
      @param { Object } data 文件中传入的数据
     @param { String } fileName 导出文件名称
*/
export const exportSubDocx = (tempDocxPath, data, fileName) => {
    // 读取并获得模板文件的二进制内容
    JSZipUtils.getBinaryContent(tempDocxPath, (error, content) => {
     // input.docx是模板。我们在导出的时候，会根据此模板来导出对应的数据
      // 抛出异常
  
       // 创建一个JSZip实例，内容为模板的内容
      const zip = new PizZip(content)
       // 创建并加载docxtemplater实例对象
      const doc = new docxtemplater().loadZip(zip)
      // 设置模板变量的值
      doc.setData({
        ...data.form,
        list: data.list
      })
      try {
        // render the document (replace all occurences of {first_name} by John, {last_name} by Doe, ...)
        doc.render()
      } catch (error) {
        const e = {
          message: error.message,
          name: error.name,
          stack: error.stack,
          properties: error.properties
        }
        console.log({
          error: e
        })
        // The error thrown here contains additional information when logged with JSON.stringify (it contains a property object).
        throw error
      }
      const out = doc.getZip().generate({
        type: 'blob',
        mimeType: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
      }) // Output the document using Data-URI
      saveAs(out, fileName)
    })
  }

