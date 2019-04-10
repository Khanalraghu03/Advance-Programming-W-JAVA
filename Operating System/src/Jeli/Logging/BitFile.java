package Jeli.Logging;

import java.io.IOException;
import java.io.OutputStream;







































































































































































































































































































































class BitFile
{
  OutputStream output_;
  byte[] buffer_;
  int index_;
  int bitsLeft_;
  
  public BitFile(OutputStream output)
  {
    output_ = output;
    buffer_ = new byte['Ā'];
    index_ = 0;
    bitsLeft_ = 8;
  }
  
  public void flush() throws IOException {
    int numBytes = index_ + (bitsLeft_ == 8 ? 0 : 1);
    if (numBytes > 0) {
      output_.write(numBytes);
      output_.write(buffer_, 0, numBytes);
      buffer_[0] = 0;
      index_ = 0;
      bitsLeft_ = 8;
    }
  }
  
  public void writeBits(int bits, int numbits) throws IOException {
    int bitsWritten = 0;
    int numBytes = 255;
    do {
      if (((index_ == 254) && (bitsLeft_ == 0)) || (index_ > 254)) {
        output_.write(numBytes);
        output_.write(buffer_, 0, numBytes);
        
        buffer_[0] = 0;
        index_ = 0;
        bitsLeft_ = 8;
      }
      
      if (numbits <= bitsLeft_) {
        int tmp91_88 = index_; byte[] tmp91_84 = buffer_;tmp91_84[tmp91_88] = ((byte)(tmp91_84[tmp91_88] | (bits & (1 << numbits) - 1) << 8 - bitsLeft_));
        bitsWritten += numbits;
        bitsLeft_ -= numbits;
        numbits = 0;
      } else {
        int tmp138_135 = index_; byte[] tmp138_131 = buffer_;tmp138_131[tmp138_135] = ((byte)(tmp138_131[tmp138_135] | (bits & (1 << bitsLeft_) - 1) << 8 - bitsLeft_));
        bitsWritten += bitsLeft_;
        bits >>= bitsLeft_;
        numbits -= bitsLeft_;
        buffer_[(++index_)] = 0;
        bitsLeft_ = 8;
      }
    } while (numbits != 0);
  }
}
