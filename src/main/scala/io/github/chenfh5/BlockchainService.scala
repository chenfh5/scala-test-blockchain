package io.github.chenfh5

import scala.collection.mutable.ListBuffer


class BlockchainService {
  var blockchain = ListBuffer[Block]()

  blockchain.append(getFristBlock)

  @deprecated
  def replaceChain(newBlockchain: ListBuffer[Block]) = {
    if (newBlockchain.size > blockchain.size) blockchain = newBlockchain
  }

  def add(newBlock: Block) = {
    if (OwnUtil.isValidBlock(getLatestBlock, newBlock)) blockchain += newBlock
    else println(s"newBlock can not add $newBlock")
  }

  def getLatestBlock = {
    blockchain.last
  }

  def getFristBlock = {
    Block(0, OwnUtil.getTsNow, "", "", "I am genesis block")
  }

}
