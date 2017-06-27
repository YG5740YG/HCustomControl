package MyView;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class PinYin {
    private void hanZiToPinYin(String scr){
        char[]chars=scr.toCharArray();
        HanyuPinyinOutputFormat format=new HanyuPinyinOutputFormat();
        StringBuffer stringBuffer=new StringBuffer();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        for (int i=0;i<chars.length;i++){
            String[]strings=null;
            try {
                strings= PinyinHelper.toHanyuPinyinStringArray(chars[i],format);
                if(strings!=null){
                    for (int j=0;j<strings.length;j++){
                        stringBuffer.append(strings[j]);
                        if(j!=strings.length-1){
                            stringBuffer.append(",");
                        }
                        stringBuffer.append("\r\n");
                    }
                }
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
        }
    }
}
