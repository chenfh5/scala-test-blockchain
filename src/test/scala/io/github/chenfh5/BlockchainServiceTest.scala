package io.github.chenfh5

import org.testng.Assert
import org.testng.annotations.Test


class BlockchainServiceTest {

  @Test(enabled = true, priority = 1)
  def sha256Test() = {
    val blockchainService = new BlockchainService()
    val res = blockchainService.getSHA256("I am Satoshi Nakamoto13")
    val expect = "0ebc56d59a34f5082aaef3d66b37a661696c2b618e62432727216ba9531041a5"
    Assert.assertTrue(expect == res)
  }

  @Test(enabled = true, priority = 2)
  def m1() = {
    /*initial*/
    val blockchainService = new BlockchainService()
    /*insert block/ generate block per 10 min*/
    for (i <- 0 to 10) {
      Thread.sleep(200) //in case of clear time distinction
      insertBlock(blockchainService, "I am " + i)
    }
    /*print the whole blockchain*/
    blockchainService.blockchain.foreach(println)
  }

  private def insertBlock(blockchainService: BlockchainService, ownData: String) = {
    val block = blockchainService.generateBlock(blockchainService.getLatestBlock, ownData)
    blockchainService.add(block)
  }

}
