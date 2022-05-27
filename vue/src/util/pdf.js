import html2canvas from "html2canvas";
import jsPDF from "jspdf";

export const downloadPDF = page => {
    html2canvas(page).then(function(canvas) {
        canvas2PDF(canvas);
    });
};

const canvas2PDF = canvas => {
    let contentWidth = canvas.width;
    let contentHeight = canvas.height;

    let imgHeight = contentHeight;
    let imgWidth = contentWidth;

    // 第一个参数： l：横向  p：纵向
    // 第二个参数：测量单位（"pt"，"mm", "cm", "m", "in" or "px"）
    let pdf = new jsPDF("l", "pt");

    pdf.addImage(
        canvas.toDataURL("image/jpeg", 1.0),
        "JPEG",
        0,
        0,
        imgWidth,
        imgHeight
    );

    pdf.save("维修委托书.pdf");
};
